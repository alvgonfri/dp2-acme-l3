
package acme.features.student.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.entities.enrolments.Activity;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentDashboardRepository extends AbstractRepository {

	@Query("select a from Activity a where a.enrolment.student.id = :id")
	Collection<Activity> findActivitiesByStudentId(int id);

	@Query("select e.course from Enrolment e where e.draftMode = 0 and e.student.id = :id")
	Collection<Course> findEnrolledCoursesByStudentId(int id);

	@Query("select lr.lecture from LectureRegistration lr where lr.course.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);

}
