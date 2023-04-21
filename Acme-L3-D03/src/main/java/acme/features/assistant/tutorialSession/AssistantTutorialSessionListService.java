
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionListService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("tutorialId", int.class);

		super.getResponse().setChecked(status);

	}

	@Override
	public void authorise() {

		boolean status;
		int tutorialId;
		Tutorial tutorial;
		Assistant assistant;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(assistant) && !tutorial.isDraftMode();

		super.getResponse().setAuthorised(status);

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<TutorialSession> objects;
		int tutorialId;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		objects = this.repository.findManyTutorialSessionByTutorialId(tutorialId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "sessionType", "startDate", "endDate");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<TutorialSession> objects) {
		assert objects != null;

		int tutorialId;
		Tutorial tutorial;
		boolean showCreate;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		showCreate = !tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		super.getResponse().setGlobal("tutorialId", tutorialId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}
