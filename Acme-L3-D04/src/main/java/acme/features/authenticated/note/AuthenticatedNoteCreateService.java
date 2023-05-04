
package acme.features.authenticated.note;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.notes.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.DefaultUserIdentity;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedNoteRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Note object;
		Date moment;
		String author;
		String username;
		String fullName;
		Principal principal;
		DefaultUserIdentity identity;

		moment = MomentHelper.getCurrentMoment();

		principal = super.getRequest().getPrincipal();
		username = principal.getUsername();
		identity = this.repository.findIdentityByUsername(username);
		fullName = identity.getFullName();
		author = username + " - " + fullName;

		object = new Note();
		object.setMoment(moment);
		object.setAuthor(author);
		object.setTitle("");
		object.setMessage("");
		object.setEmail("");
		object.setMoreInfo("");

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {
		assert object != null;

		super.bind(object, "moment", "author", "title", "message", "email", "moreInfo");
	}

	@Override
	public void validate(final Note object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Note object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "author", "title", "message", "email", "moreInfo");
		tuple.put("confirmation", false);

		super.getResponse().setData(tuple);
	}

}
