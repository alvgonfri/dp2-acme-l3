
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int						numberOfPrincipalsAssistant;
	
	int						numberOfPrincipalsAuditor;
	
	int						numberOfPrincipalsCompany;
	
	int						numberOfPrincipalsStudent;
	
	int						numberOfPrincipalLecturer;
	
	double						ratioOfPeepsWithEmailAndLink;
	double						ratioOfCriticalBulletins;
	double						ratioOfNonCriticalBulletins;
	double						averageBudgetInOffersByCurrency;
	double						minimumBudgetInOffersByCurrency;
	double						maximumBudgetInOffersByCurrency;
	double						deviationBudgetInOffersByCurrency;
	double						averageNumberOfNotesPostedOver10Weeks;
	double						minimumNumberOfNotesPostedOver10Weeks;
	double						maximumNumberOfNotesPostedOver10Weeks;
	double						deviationNumberOfNotesPostedOver10Weeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
