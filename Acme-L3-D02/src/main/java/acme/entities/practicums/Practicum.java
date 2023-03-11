
package acme.entities.practicums;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
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

	// Derived attributes -----------------------------------------------------

	//	@Transient
	//	public Integer estimatedTime() {
	//		int result = 0;
	//		for (final PracticumSession session : this.sessions) {
	//			final int diffInMillies = (int) Math.abs(session.getEndDate().getTime() - session.getStartDate().getTime());
	//			final int diffInHours = (int) TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	//			result += diffInHours;
	//		}
	//		return result;
	//	}

	// Relationships ----------------------------------------------------------

	//	protected List<PracticumSession>	sessions;

}
