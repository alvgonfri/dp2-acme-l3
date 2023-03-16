
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							numberOfTheoryTutorials;
	int							numberOfHandsOnTutorials;
	double						averagePeriodOfTheAssistantSessions;
	double						minimumPeriodOfTheAssistantSessions;
	double						maximumPeriodOfTheAssistantSessions;
	double						deviationOfThePeriodOfTheAssistantSessions;
	double						averageTimeOfTheTutorials;
	double						minimumTimeOfTheTutorials;
	double						maximumTimeOfTheTutorials;
	double						deviationTimeOfTheTutorials;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
