
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.LectureType;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityDeleteService extends AbstractService<Student, Activity> {

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
		int activityId;
		Enrolment enrolment;

		activityId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findOneEnrolmentByActivityId(activityId);
		status = enrolment != null && !enrolment.isDraftMode() && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());

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
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "summary", "type", "startDate", "endDate", "moreInfo");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(LectureType.class, object.getType());

		tuple = super.unbind(object, "title", "summary", "type", "startDate", "endDate", "moreInfo");
		tuple.put("enrolmentId", object.getEnrolment().getId());
		tuple.put("draftMode", object.getEnrolment().isDraftMode());
		tuple.put("types", choices);

		super.getResponse().setData(tuple);
	}

}
