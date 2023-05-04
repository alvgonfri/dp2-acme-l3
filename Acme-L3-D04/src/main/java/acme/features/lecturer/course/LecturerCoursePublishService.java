
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.entities.courses.LectureType;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


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
		Course course;
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(masterId);
		lecturer = course == null ? null : course.getLecturer();
		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
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
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "summary", "retailPrice", "moreInfo");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		Collection<Lecture> lectures;
		boolean allLecturesArePublished;
		final boolean courseTypesIsNotTheory;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;

			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "lecturer.course.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getAmount() >= 0, "salary", "lecturer.course.form.error.negative-price");

		lectures = this.repository.findManyLecturesByCourseId(object.getId());

		if (!lectures.isEmpty()) {
			allLecturesArePublished = lectures.stream().allMatch(l -> !l.isDraftMode());
			super.state(allLecturesArePublished, "*", "lecturer.course.form.error.unpublishedLectures");
			courseTypesIsNotTheory = lectures.stream().anyMatch(l -> l.getLectureType().equals(LectureType.HANDS_ON));
			super.state(courseTypesIsNotTheory, "*", "lecturer.course.form.error.allLecturesAreTheory");

		}

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "summary", "retailPrice", "moreInfo", "draftMode");

		super.getResponse().setData(tuple);
	}

}
