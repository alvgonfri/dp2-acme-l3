
package acme.features.student.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentCourseRepository repository;

	// AbstractService interface ----------------------------------------------


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
		object = this.repository.findOneCourseById(id);

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

		super.getResponse().setData(tuple);
	}

}
