
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.LectureType;
import acme.entities.enrolments.Activity;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityShowService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

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
		Activity activity;
		Student student;

		masterId = super.getRequest().getData("id", int.class);
		activity = this.repository.findOneActivityById(masterId);
		student = activity == null ? null : activity.getEnrolment().getStudent();
		status = super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(LectureType.class, object.getType());

		tuple = super.unbind(object, "enrolment.code", "title", "summary", "type", "startDate", "endDate", "moreInfo");
		tuple.put("types", choices);

		super.getResponse().setData(tuple);
	}

}
