
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Lecture;
import acme.entities.courses.LectureRegistration;
import acme.entities.courses.LectureType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureDeleteService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


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
		Lecture Lecture;
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		Lecture = this.repository.findOneLectureById(masterId);
		lecturer = Lecture == null ? null : Lecture.getLecturer();
		status = Lecture != null && Lecture.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "summary", "learningTime", "body", "lectureType", "moreInfo");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		Collection<LectureRegistration> lectureCourses;

		lectureCourses = this.repository.findManyLectureCoursesByLectureId(object.getId());

		this.repository.deleteAll(lectureCourses);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(LectureType.class, object.getLectureType());

		tuple = super.unbind(object, "title", "summary", "learningTime", "body", "lectureType", "moreInfo", "draftMode");
		tuple.put("lectureTypes", choices);

		super.getResponse().setData(tuple);
	}

}
