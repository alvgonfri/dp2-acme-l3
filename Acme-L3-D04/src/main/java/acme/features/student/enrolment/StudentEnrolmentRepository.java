
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("select e from Enrolment e where e.code = :code")
	Enrolment findOneEnrolmentByCode(String code);

	@Query("select e from Enrolment e where e.student.id = :studentId")
	Collection<Enrolment> findEnrolmentsByStudentId(int studentId);

	@Query("select a from Activity a where a.enrolment.id = :enrolmentId")
	Collection<Activity> findActivitiesByEnrolmentId(int enrolmentId);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select c from Course c where c.draftMode = 0")
	Collection<Course> findPublishedCourses();

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select s from Student s where s.id = :id")
	Student findOneStudentById(int id);

}
