
package acme.features.company.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("select c from Company c where c.userAccount.id = ?1")
	Company findOneCompanyByUserAccountId(int userAccountId);

	@Query("select count(sp) from PracticumSession sp where sp.practicum.company.id = ?1")
	Integer findCountSession(int companyId);

	@Query("select avg(datediff(sp.endDate,sp.startDate)), 0.0 from PracticumSession sp where sp.practicum.company.id = ?1")
	Double findAverageSessionLength(int companyId);

	@Query("select stddev(datediff(sp.endDate,sp.startDate)), 0.0 from PracticumSession sp where sp.practicum.company.id = ?1")
	Double findDeviationSessionLength(int companyId);

	@Query("select min(datediff(sp.endDate,sp.startDate)), 0.0 from PracticumSession sp where sp.practicum.company.id = ?1")
	Double findMinimumSessionLength(int companyId);

	@Query("select max(datediff(sp.endDate,sp.startDate)), 0.0 from PracticumSession sp where sp.practicum.company.id = ?1")
	Double findMaximumSessionLength(int companyId);

	@Query("select avg((select sum(datediff(sp.endDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0 from Practicum p where p.company.id = ?1")
	Double findAveragePracticaLength(int companyId);

	@Query("select stddev((select sum(datediff(sp.endDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0 from Practicum p where p.company.id = ?1")
	Double findDeviationPracticaLength(int companyId);

	@Query("select min((select sum(datediff(sp.endDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0 from Practicum p where p.company.id = ?1")
	Double findMinimumPracticaLength(int companyId);

	@Query("select max((select sum(datediff(sp.endDate,sp.startDate)) from PracticumSession sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0 from Practicum p where p.company.id = ?1")
	Double findMaximumPracticaLength(int companyId);

	@Query("select count(p) from Practicum p where p.company.id = ?1")
	Integer findCountPractica(int companyId);

	@Query("SELECT FUNCTION('MONTH', sp.startDate), COUNT(sp) FROM PracticumSession sp WHERE sp.practicum.company.id = ?1 GROUP BY FUNCTION('MONTH', sp.startDate) ORDER BY COUNT(sp) DESC")
	List<Object[]> findTotalNumberOfPracticaByMonth(int companyId);

}
