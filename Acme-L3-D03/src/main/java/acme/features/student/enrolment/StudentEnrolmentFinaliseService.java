
package acme.features.student.enrolment;

import org.springframework.stereotype.Service;

import acme.entities.enrolments.Enrolment;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

}
