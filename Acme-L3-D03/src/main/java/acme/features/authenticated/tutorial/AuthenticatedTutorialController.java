
package acme.features.authenticated.tutorial;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedTutorialController extends AbstractController<Authenticated, Tutorial> {

	@Autowired
	protected AuthenticatedTutorialListService	listService;

	@Autowired
	protected AuthenticatedTutorialShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
