
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("select ar from AuditingRecord ar where ar.id = :id")
	AuditingRecord findOneAuditingRecordById(int id);

	@Query("select ar from AuditingRecord ar where ar.audit.id = :auditId")
	Collection<AuditingRecord> findManyAuditingRecordByAuditId(int auditId);

	@Query("select a from Audit a where a.id = :auditId")
	Audit findOneAuditById(int auditId);

	@Query("select ar.audit from AuditingRecord ar where ar.id = :id")
	Audit findOneAuditByAuditingRecordId(int id);

	@Query("select a from Auditor a where a.userAccount.username = :username")
	Auditor findOneAuditorByUsername(String username);

}
