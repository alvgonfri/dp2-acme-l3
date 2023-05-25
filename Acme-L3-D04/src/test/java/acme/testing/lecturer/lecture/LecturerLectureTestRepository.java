
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.courses.Lecture;
import acme.framework.repositories.AbstractRepository;

public interface LecturerLectureTestRepository extends AbstractRepository {

	@Query("select lc.lecture from LectureRegistration lc where lc.course.lecturer.userAccount.username = :userName")
	Collection<Lecture> findManyLecturesByUserName(String userName);

}
