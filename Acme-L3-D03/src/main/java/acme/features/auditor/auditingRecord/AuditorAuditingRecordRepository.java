
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("select ar from AuditingRecord ar where ar.id = :id")
	AuditingRecord findOneAuditingRecordById(int id);

	@Query("select ar from AuditingRecord ar where ar.audit.id = :auditId")
	Collection<AuditingRecord> findManyAuditingRecordByAuditId(int auditId);

	//en repo de audit (?)
	//	@Query("select a from Audit a where a.id = :auditId")
	//	Audit findOneAuditById(int auditId);

}
