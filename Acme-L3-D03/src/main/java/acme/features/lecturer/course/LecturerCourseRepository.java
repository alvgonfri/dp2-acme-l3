
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.entities.courses.LectureRegistration;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.id = :lecturerId")
	Collection<Course> findCoursesByLecturerId(int lecturerId);

	@Query("select l from Lecturer l where l.id = :lecturerId")
	Lecturer findOneLecturerById(int lecturerId);

	@Query("select c from Course c where c.id = :courseId")
	Course findOneCourseById(int courseId);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select lr from LectureRegistration lr where lr.course.id = :courseId")
	Collection<LectureRegistration> findManyLectureCoursesByCourseId(int courseId);

	@Query("select lr.lecture from LectureRegistration lr where lr.course.id = :courseId")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);
}
