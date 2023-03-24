
package acme.features.authenticated.note;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.notes.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedNoteController extends AbstractController<Authenticated, Note> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedNoteListRecentService	listRecentService;

	@Autowired
	protected AuthenticatedNoteShowService			showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listRecentService);
		super.addBasicCommand("show", this.showService);
	}

}
