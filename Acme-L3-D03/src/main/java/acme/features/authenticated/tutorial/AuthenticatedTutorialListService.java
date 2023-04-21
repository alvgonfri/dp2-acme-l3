
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialListService extends AbstractService<Authenticated, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = true;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Tutorial> object;
		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);
		object = this.repository.findTutorialByCourseId(courseId);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "summary");

		super.getResponse().setData(tuple);
	}

}
