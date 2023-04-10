
package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.audits.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordUpdateService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		int masterId;
		Audit audit;

		masterId = super.getRequest().getData("id", int.class);
		audit = this.repository.findOneAuditByAuditingRecordId(masterId);
		status = audit != null && audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		AuditingRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditingRecordById(id);

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
		tuple.put("auditId", object.getAudit().getId());
		tuple.put("draftMode", object.getAudit().isDraftMode());

		super.getResponse().setData(tuple);
	}
}
