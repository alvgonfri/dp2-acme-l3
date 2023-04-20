
package acme.features.assistant.tutorial;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.tutorial.Tutorial;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantTutorialController extends AbstractController<Assistant, Tutorial> {

	@Autowired
	protected AssistantTutorialCreateService	createService;

	@Autowired
	protected AssistantTutorialShowService		showService;

	@Autowired
	protected AssistantTutorialListService		listService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("list", this.listService);

	}

}
