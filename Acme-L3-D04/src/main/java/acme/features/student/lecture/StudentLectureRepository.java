
package acme.features.student.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select c from Course c where c.id = :masterId")
	Course findOneCourseById(int masterId);

	@Query("select lr.lecture from LectureRegistration lr where lr.course.id = :courseId")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);

	@Query("select lr.course from LectureRegistration lr where lr.lecture.id = :id")
	Collection<Course> findCoursesByLectureId(final int id);

}
