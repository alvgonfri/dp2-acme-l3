
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor.id = :auditorId")
	Collection<Audit> findManyAuditsByAuditorId(int auditorId);

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select a from Audit a where a.code = :code")
	Audit findOneAuditByCode(String code);

	@Query("Select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select ar from AuditingRecord ar where ar.audit.id = :id")
	Collection<AuditingRecord> findManyAuditingRecordsByAuditId(int id);

}
