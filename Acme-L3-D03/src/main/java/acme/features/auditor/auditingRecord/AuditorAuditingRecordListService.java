
package acme.features.auditor.auditingRecord;

import java.time.Duration;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordListService extends AbstractService<Auditor, AuditingRecord> {

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
		int auditId;
		Audit audit;
		Auditor loggedAuditor;

		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findOneAuditById(auditId);
		loggedAuditor = this.repository.findOneAuditorByUsername(super.getRequest().getPrincipal().getUsername());
		System.out.println("esto" + loggedAuditor + audit.getAuditor());
		status = audit != null && loggedAuditor == audit.getAuditor();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditingRecord> objects;
		int auditId;

		auditId = super.getRequest().getData("auditId", int.class);
		objects = this.repository.findManyAuditingRecordByAuditId(auditId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;
		Duration auditingDuration;

		auditingDuration = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate());

		tuple = super.unbind(object, "assessment", "startDate", "endDate", "mark", "moreInfo", "subject", "isCorrection");
		tuple.put("auditingDuration", String.format("%dD %dH %dMin", auditingDuration.toDays(), auditingDuration.toHours() % 24, auditingDuration.toMinutes() % 60));
		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<AuditingRecord> objects) {
		assert objects != null;

		int auditId;
		boolean showCreate;
		final Audit audit;

		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findOneAuditById(auditId);
		showCreate = audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setGlobal("auditId", auditId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}
