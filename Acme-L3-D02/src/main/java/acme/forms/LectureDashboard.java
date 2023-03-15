
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							numberOfTheoryActivites;
	int							numberOfHandsOnActivites;
	double						averageLearningTimeLectures;
	double						deviationLearningTimeLectures;
	double						minimumLearningTimeLectures;
	double						maximumLearningTimeLectures;
	double						averageLearningTimeCourses;
	double						deviationLearningTimeCourses;
	double						minimumLearningTimeCourses;
	double						maximumLearningTimeCourses;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
