
package acme.entities.practicums;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.courses.Course;
import acme.framework.data.AbstractRole;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "[A-Z]{1,3}\\d{3}")
	@NotBlank
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			summary; // "abstract" name is banned

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	protected boolean			draftMode;

	// Derived attributes -----------------------------------------------------


	public Double estimatedTime(final Collection<PracticumSession> sessions) {
		double estimatedTime = 0;
		if (sessions.size() > 0)
			for (final PracticumSession session : sessions) {
				final long diffInMilliseconds = session.getEndDate().getTime() - session.getStartDate().getTime();
				final double diffInHours = diffInMilliseconds / (1000.0 * 60 * 60);
				estimatedTime = estimatedTime + diffInHours;
			}
		return estimatedTime;
	}

	// Relationships ----------------------------------------------------------


	//	protected Collection<PracticumSession>	sessions;
	@ManyToOne(optional = false)
	@Valid
	@NotNull
	protected Course	course;

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	protected Company	company;

}
