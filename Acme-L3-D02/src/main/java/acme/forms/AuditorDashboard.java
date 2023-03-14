
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							numberOfAudits;
	int							numberOfAuditsForHandsOnCourses;
	double						averageAuditingRecordsInTheAuditorsAudits;
	double						minimumAuditingRecordsInTheAuditorsAudits;
	double						maximumAuditingRecordsInTheAuditorsAudits;
	double						deviationOfTheAuditingRecordsInTheAuditorAudits;
	double						averageTimeOfTheAuditorAuditingRecords;
	double						minimumTimeOfTheAuditorAuditingRecords;
	double						maximumTimeOfTheAuditorAuditingRecords;
	double						deviationOfTheTimeOfTheAuditorAuditingRecords;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
