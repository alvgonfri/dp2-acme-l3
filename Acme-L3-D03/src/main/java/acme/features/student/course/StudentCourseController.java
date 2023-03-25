
package acme.features.student.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.courses.Course;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentCourseController extends AbstractController<Student, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentCourseListAllService	listAllService;

	@Autowired
	protected StudentCourseShowService		showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAllService);
		super.addBasicCommand("show", this.showService);
	}

}
