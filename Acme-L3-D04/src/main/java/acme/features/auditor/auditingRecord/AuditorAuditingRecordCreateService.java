
package acme.features.auditor.auditingRecord;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.audits.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordCreateService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("auditId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		int masterId;
		Audit audit;

		masterId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findOneAuditById(masterId);
		status = audit != null && audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		AuditingRecord object;
		int masterId;
		Audit audit;

		masterId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findOneAuditById(masterId);

		object = new AuditingRecord();
		object.setAudit(audit);
		object.setCorrection(false);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {

		assert object != null;

		super.bind(object, "audit.code", "subject", "assessment", "startDate", "endDate", "mark", "moreInfo");

	}

	@Override
	public void validate(final AuditingRecord object) {

		assert object != null;

		Calendar calendar;
		final Date earliestDate;

		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2000);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		earliestDate = new Date(calendar.getTimeInMillis());

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate")) {
			boolean isValid;

			isValid = MomentHelper.isLongEnough(object.getStartDate(), object.getEndDate(), 1, ChronoUnit.HOURS) && MomentHelper.isBefore(object.getStartDate(), object.getEndDate());

			super.state(isValid, "startDate", "auditor.auditingRecord.form.error.duration");
			super.state(isValid, "endDate", "auditor.auditingRecord.form.error.duration");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {

			boolean isValid;

			isValid = MomentHelper.isAfterOrEqual(object.getStartDate(), earliestDate);
			super.state(isValid, "startDate", "auditor.auditingRecord.form.error.earliesdate");
		}

	}

	@Override
	public void perform(final AuditingRecord object) {

		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {

		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "audit.code", "subject", "assessment", "startDate", "endDate", "mark", "moreInfo");
		tuple.put("mark", choices);
		tuple.put("auditId", super.getRequest().getData("auditId", int.class));
		tuple.put("draftMode", object.getAudit().isDraftMode());

		super.getResponse().setData(tuple);
	}

}
