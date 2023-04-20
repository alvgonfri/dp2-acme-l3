/*
 * AuthenticatedConsumerController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.company.practicum;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicums.Practicum;
import acme.entities.practicums.PracticumSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumPublishService extends AbstractService<Company, Practicum> {

	// Constants -------------------------------------------------------------
	protected static final String[]			PROPERTIES	= {
		"code", "title", "summary", "goals", "estimatedTime"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	protected CompanyPracticumRepository	repository;


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
		int practicumId;
		Practicum practicum;
		Company company;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		company = practicum == null ? null : practicum.getCompany();
		status = practicum != null && practicum.isDraftMode() && principal.hasRole(company);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum practicum;
		int practicumId;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		super.getBuffer().setData(practicum);
	}

	@Override
	public void bind(final Practicum practicum) {
		assert practicum != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(practicum, CompanyPracticumPublishService.PROPERTIES);
		practicum.setCourse(course);
	}

	@Override
	public void validate(final Practicum practicum) {
		assert practicum != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean isUnique;
			boolean noChangeCode;
			Practicum oldPracticum;
			int practicumId;

			practicumId = super.getRequest().getData("id", int.class);
			oldPracticum = this.repository.findPracticumById(practicumId);
			isUnique = this.repository.findPracticumByCode(practicum.getCode()).isEmpty();
			noChangeCode = oldPracticum.getCode().equals(practicum.getCode()) && oldPracticum.getId() == practicum.getId();

			super.state(isUnique || noChangeCode, "code", "company.practicum.form.error.not-unique-code");
		}

		// Únicamente se deberá de comprobar que el tiempo estimado es correcto cuando se va a publicar.
		if (!super.getBuffer().getErrors().hasErrors("estimatedTime")) {
			Collection<PracticumSession> sessions;
			final double estimatedTime;
			double totalHours;
			// Menos de 10% de diferencia entre el tiempo estimado y el tiempo real.
			boolean moreThan90Percent;
			boolean lessThan110Percent;

			sessions = this.repository.findPracticumSessionsByPracticumId(practicum.getId());
			estimatedTime = practicum.estimatedTime(sessions);

			totalHours = sessions.stream().mapToDouble(session -> {
				Date start;
				Date end;
				Duration duration;

				start = session.getStartDate();
				end = session.getEndDate();
				duration = MomentHelper.computeDuration(start, end);

				return duration.toHours();
			}).sum();

			moreThan90Percent = totalHours >= estimatedTime * 0.9;
			lessThan110Percent = totalHours <= estimatedTime * 1.1;

			super.state(moreThan90Percent && lessThan110Percent, "estimatedTime", "company.practicum.form.error.not-in-range");
		}
	}

	@Override
	public void perform(final Practicum practicum) {
		assert practicum != null;

		practicum.setDraftMode(false);

		this.repository.save(practicum);
	}

	@Override
	public void unbind(final Practicum practicum) {
		assert practicum != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", practicum.getCourse());

		tuple = super.unbind(practicum, CompanyPracticumPublishService.PROPERTIES);
		tuple.put("draftMode", practicum.isDraftMode());
		tuple.put("course", choices);
		tuple.put("courses", courses);

		super.getResponse().setData(tuple);
	}
}
