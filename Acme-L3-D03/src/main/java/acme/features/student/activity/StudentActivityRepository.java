
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.id = :id")
	Activity findOneActivityById(int id);

	@Query("select e from Enrolment e where e.id = :enrolmentId")
	Enrolment findOneEnrolmentById(int enrolmentId);

	@Query("select a from Activity a where a.enrolment.id = :enrolmentId")
	Collection<Activity> findManyActivitiesByEnrolmentId(int enrolmentId);

	@Query("select a.enrolment from Activity a where a.id = :activityId")
	Enrolment findOneEnrolmentByActivityId(int activityId);

}
