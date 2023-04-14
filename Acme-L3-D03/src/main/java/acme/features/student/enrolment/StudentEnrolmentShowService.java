
package acme.features.student.enrolment;

import java.time.Duration;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

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
		Enrolment enrolment;
		Student student;

		masterId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findOneEnrolmentById(masterId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;
		int workTime;
		Collection<Course> courses;
		SelectChoices choices;

		courses = this.repository.findPublishedCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		workTime = this.getWorkTime(object.getId());

		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode", "creditCardHolder");
		tuple.put("workTime", workTime);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

	// Aux --------------------------------------------------------------------

	public int getWorkTime(final int enrolmentId) {
		int result = 0;
		final Collection<Activity> activities = this.repository.findActivitiesByEnrolmentId(enrolmentId);
		for (final Activity activity : activities) {
			final Duration duration = MomentHelper.computeDuration(activity.getStartDate(), activity.getEndDate());
			final int diffInHours = (int) duration.toHours();
			result += diffInHours;
		}
		return result;
	}

}
