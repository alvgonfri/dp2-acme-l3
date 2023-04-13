
package acme.features.student.enrolment;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

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
		Enrolment object;
		Student student;

		student = this.repository.findOneStudentById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Enrolment();
		object.setDraftMode(true);
		object.setStudent(student);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		final int LOWER_NIBBLE_START = 12;

		int courseId;
		Course course;
		String creditCardNumber;
		String creditCardLowerNibble;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);

		creditCardNumber = super.getRequest().getData("creditCardNumber", String.class);

		super.bind(object, "code", "motivation", "goals", "creditCardHolder");
		object.setCourse(course);
		if (creditCardNumber.length() == 16) {
			creditCardLowerNibble = creditCardNumber.substring(LOWER_NIBBLE_START);
			object.setCreditCardLowerNibble(creditCardLowerNibble);
		}
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment existing;

			existing = this.repository.findOneEnrolmentByCode(object.getCode());
			super.state(existing == null, "code", "student.enrolment.form.error.code-duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("creditCardNumber")) {
			String creditCardNumber;

			creditCardNumber = super.getRequest().getData("creditCardNumber", String.class);
			if (!creditCardNumber.equals(""))
				super.state(creditCardNumber.matches("\\d{16}"), "creditCardNumber", "student.enrolment.form.error.wrong-cardNumber");
		}

		if (!super.getBuffer().getErrors().hasErrors("expiryDate")) {
			Date expiryDate;

			expiryDate = super.getRequest().getData("expiryDate", Date.class);
			if (expiryDate != null)
				super.state(MomentHelper.isFuture(expiryDate), "expiryDate", "student.enrolment.form.error.card-expired");
		}

		if (!super.getBuffer().getErrors().hasErrors("cvc")) {
			String cvc;

			cvc = super.getRequest().getData("cvc", String.class);
			if (!cvc.equals(""))
				super.state(cvc.matches("\\d{3}"), "cvc", "student.enrolment.form.error.wrong-cvc");
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "motivation", "goals", "draftMode", "creditCardHolder");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
