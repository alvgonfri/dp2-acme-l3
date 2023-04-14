
package acme.features.authenticated.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyCourseShowService extends AbstractService<Any, Course> {

	@Autowired
	protected AnyCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		String lecturerFullName;
		Tuple tuple;

		lecturerFullName = object.getLecturer().getUserAccount().getIdentity().getFullName();

		tuple = super.unbind(object, "code", "title", "summary", "retailPrice", "moreInfo");
		tuple.put("lecturerFullName", lecturerFullName);
		tuple.put("confirmation", false);
		tuple.put("readonly", true);

		super.getResponse().setData(tuple);
	}
}
