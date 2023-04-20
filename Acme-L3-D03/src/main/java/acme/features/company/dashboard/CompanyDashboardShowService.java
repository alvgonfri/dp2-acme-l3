
package acme.features.company.dashboard;

import java.time.Month;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.CompanyDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyDashboardShowService extends AbstractService<Company, CompanyDashboard> {

	// Constants --------------------------------------------------------------
	protected static final String[]		PROPERTIES	= {
		"practicaNumberPerMonth", "averagePeriodLengthSession", "deviationPeriodLengthSession", "minimumPeriodLengthSession", "maximumPeriodLengthSession", "averagePeriodLengthPractica", "deviationPeriodLengthPractica", "minimumPeriodLengthPractica",
		"maximumPeriodLengthPractica"
	};

	// Internal state ---------------------------------------------------------
	@Autowired
	private CompanyDashboardRepository	repository;


	// AbstractService Interface ----------------------------------------------
	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		Company company;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);

		status = company != null && principal.hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int companyId;
		CompanyDashboard companyDashboard;
		final Principal principal;
		int userAccountId;
		Company company;
		companyDashboard = new CompanyDashboard();

		final double averagePeriodLengthSession;
		final double deviationPeriodLengthSession;
		final double minimumPeriodLengthSession;
		final double maximumPeriodLengthSession;
		int countSession;

		final double averagePeriodLengthPractica;
		final double deviationPeriodLengthPractica;
		final double minimumPeriodLengthPractica;
		final double maximumPeriodLengthPractica;
		int countPractica;

		final Map<String, Integer> practicaNumberPerMonth;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);
		companyId = company.getId();

		practicaNumberPerMonth = this.repository.findTotalNumberOfPracticaByMonth(companyId).stream().collect(Collectors.toMap(key -> Month.of((int) key[0]).toString(), value -> (int) value[1]));

		averagePeriodLengthSession = this.repository.findAverageSessionLength(companyId);
		deviationPeriodLengthSession = this.repository.findDeviationSessionLength(companyId);
		minimumPeriodLengthSession = this.repository.findMinimumSessionLength(companyId);
		maximumPeriodLengthSession = this.repository.findMaximumSessionLength(companyId);
		countSession = this.repository.findCountSession(companyId);

		averagePeriodLengthPractica = this.repository.findAveragePracticaLength(companyId);
		deviationPeriodLengthPractica = this.repository.findDeviationPracticaLength(companyId);
		minimumPeriodLengthPractica = this.repository.findMinimumPracticaLength(companyId);
		maximumPeriodLengthPractica = this.repository.findMaximumPracticaLength(companyId);
		countPractica = this.repository.findCountPractica(companyId);

		companyDashboard.setPracticaNumberPerMonth(practicaNumberPerMonth);

		companyDashboard.setAveragePeriodLengthSession(averagePeriodLengthSession);
		companyDashboard.setDeviationPeriodLengthSession(deviationPeriodLengthSession);
		companyDashboard.setMinimumPeriodLengthSession(minimumPeriodLengthSession);
		companyDashboard.setMaximumPeriodLengthSession(maximumPeriodLengthSession);

		companyDashboard.setAveragePeriodLengthPractica(averagePeriodLengthPractica);
		companyDashboard.setDeviationPeriodLengthPractica(deviationPeriodLengthPractica);
		companyDashboard.setMinimumPeriodLengthPractica(minimumPeriodLengthPractica);
		companyDashboard.setMaximumPeriodLengthPractica(maximumPeriodLengthPractica);

		super.getBuffer().setData(companyDashboard);
	}

	@Override
	public void unbind(final CompanyDashboard companyDashboard) {
		Tuple tuple;

		tuple = super.unbind(companyDashboard, CompanyDashboardShowService.PROPERTIES);

		super.getResponse().setData(tuple);
	}
}
