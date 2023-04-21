
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicums.PracticumSession;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialUnpublishService extends AbstractService<Assistant, Tutorial> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

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
		Tutorial tutorial;
		Assistant assistant;

		practicumId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(practicumId);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = tutorial != null && !tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(assistant);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "code", "title", "summary", "goals");

		object.setCourse(course);

	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;

	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;
		object.setDraftMode(true);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		final Collection<PracticumSession> sessions;
		final Double estimatedTime;
		Tuple tuple;

		courses = this.repository.findPublishedCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "summary", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
