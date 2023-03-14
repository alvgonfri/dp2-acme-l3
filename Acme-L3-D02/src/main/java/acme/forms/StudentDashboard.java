
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							numberOfTheoryActivites;
	int							numberOfHandsOnActivites;
	double						averagePeriodOfTheStudentActivities;
	double						minimumPeriodOfTheStudentActivities;
	double						maximumPeriodOfTheStudentActivities;
	double						deviationOfThePeriodOfTheStudentActivities;
	double						averageLearningTimeOfTheEnrolledCourses;
	double						minimumLearningTimeOfTheEnrolledCourses;
	double						maximumLearningTimeOfTheEnrolledCourses;
	double						deviationLearningTimeOfTheEnrolledCourses;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
