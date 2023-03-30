
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolments.Activity;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.id = :id")
	Activity findOneActivityById(int id);

	@Query("select a from Activity a where a.enrolment.student.id = :studentId")
	Collection<Activity> findActivitiesByStudentId(int studentId);

}
