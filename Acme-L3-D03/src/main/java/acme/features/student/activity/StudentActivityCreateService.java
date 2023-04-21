
package acme.features.student.activity;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.LectureType;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("enrolmentId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		status = enrolment != null && !enrolment.isDraftMode() && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);

		object = new Activity();
		object.setEnrolment(enrolment);

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

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Calendar calendar;
			final Date date;

			calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, 2000);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			date = new Date(calendar.getTimeInMillis());

			super.state(MomentHelper.isAfterOrEqual(object.getStartDate(), date), "startDate", "student.activity.form.error.wrong-start");
		}
		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			Calendar calendar;
			final Date date;

			calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, 2100);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			date = new Date(calendar.getTimeInMillis());

			super.state(MomentHelper.isBeforeOrEqual(object.getEndDate(), date), "endDate", "student.activity.form.error.wrong-end");
		}
		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "endDate", "student.activity.form.error.wrong-dates");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(LectureType.class, object.getType());

		tuple = super.unbind(object, "title", "summary", "type", "startDate", "endDate", "moreInfo");
		tuple.put("enrolmentId", super.getRequest().getData("enrolmentId", int.class));
		tuple.put("draftMode", object.getEnrolment().isDraftMode());
		tuple.put("types", choices);

		super.getResponse().setData(tuple);
	}

}
