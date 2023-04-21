
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

	@Autowired
	protected AssistantTutorialRepository repository;


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
		Tutorial tutorial;
		Assistant assistant;

		masterId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = super.getRequest().getPrincipal().hasRole(assistant);

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
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		int totalTime;
		Collection<Course> courses;
		SelectChoices choices;
		Collection<TutorialSession> sessions;

		courses = this.repository.findPublishedCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		sessions = this.repository.findTutorialSessionByTutorialId(object.getId());
		totalTime = object.totalTime(sessions);

		tuple = super.unbind(object, "code", "title", "summary", "goals", "draftMode");
		tuple.put("totalTime", totalTime);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
