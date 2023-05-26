
package acme.testing.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;

public interface LecturerCourseTestRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.userAccount.username = :userName")
	Collection<Course> findManyCoursesByUserName(String userName);

	@Query("select c from Course c where c.code = :code")
	Course findCourseByCode(String code);

}
