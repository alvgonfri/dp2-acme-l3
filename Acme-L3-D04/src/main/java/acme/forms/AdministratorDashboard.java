
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							numberOfAssistants;
	int							numberOfAuditors;
	int							numberOfCompanies;
	int							numberOfStudents;
	int							numberOfLecturers;

	double						ratioOfPeepsWithEmailAndLink;
	double						ratioOfCriticalBulletins;
	double						ratioOfNonCriticalBulletins;
	Map<String, Double>			averageBudgetInOffersByCurrency;
	Map<String, Double>			minimumBudgetInOffersByCurrency;
	Map<String, Double>			maximumBudgetInOffersByCurrency;
	Map<String, Double>			deviationBudgetInOffersByCurrency;
	double						averageNotesLastTenWeeks;
	double						minimumNotesLastTenWeeks;
	double						maximumNotesLastTenWeeks;
	double						deviationOfNotesLastTenWeeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
