
package acme.features.student.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.enrolments.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentActivityController extends AbstractController<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityListService	listService;

	@Autowired
	protected StudentActivityShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
