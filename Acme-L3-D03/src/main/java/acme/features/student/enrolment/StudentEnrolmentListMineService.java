
package acme.features.student.enrolment;

import java.time.Duration;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentListMineService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

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
		Collection<Enrolment> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findEnrolmentsByStudentId(principal.getActiveRoleId());

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;
		int workTime;

		workTime = this.getWorkTime(object.getId());
		tuple = super.unbind(object, "code", "motivation", "course.title");
		tuple.put("workTime", workTime);

		super.getResponse().setData(tuple);
	}

	// Aux --------------------------------------------------------------------

	private int getWorkTime(final int enrolmentId) {
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
