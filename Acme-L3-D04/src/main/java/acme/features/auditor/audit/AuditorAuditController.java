
package acme.features.auditor.audit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audits.Audit;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditController extends AbstractController<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditListMineService	auditorAuditListMineService;

	@Autowired
	protected AuditorAuditShowService		auditorAuditShowService;

	@Autowired
	protected AuditorAuditCreateService		auditorAuditCreateService;

	@Autowired
	protected AuditorAuditDeleteService		auditorAuditDeleteService;

	@Autowired
	protected AuditorAuditUpdateService		auditorAuditUpdateService;

	@Autowired
	protected AuditorAuditPublishService	auditorAuditPublishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.auditorAuditShowService);
		super.addBasicCommand("create", this.auditorAuditCreateService);
		super.addBasicCommand("delete", this.auditorAuditDeleteService);
		super.addBasicCommand("update", this.auditorAuditUpdateService);
		super.addCustomCommand("list-mine", "list", this.auditorAuditListMineService);
		super.addCustomCommand("publish", "update", this.auditorAuditPublishService);

	}

}
