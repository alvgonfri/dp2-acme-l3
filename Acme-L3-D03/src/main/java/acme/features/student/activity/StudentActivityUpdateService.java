
package acme.features.student.activity;

import org.springframework.stereotype.Service;

import acme.entities.enrolments.Activity;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

}
