
package acme.features.authenticated.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.course.id = :id and a.draftMode = false")
	Collection<Audit> findManyPublishedAuditsByCourseId(int id);

	@Query("select ar from AuditingRecord ar where ar.audit.id = :auditId")
	Collection<AuditingRecord> findManyAuditingRecordsByAuditId(int auditId);

	@Query("Select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);
}
