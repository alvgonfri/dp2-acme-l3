
package acme.entities.enrolments;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	@NotBlank
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			motivation;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	// Derived attributes -----------------------------------------------------

	/*
	 * @Transient
	 * public Integer workTime() {
	 * int result = 0;
	 * for (final Activity activity : this.activities) {
	 * final int diffInMillies = (int) Math.abs(activity.getEndDate().getTime() - activity.getStartDate().getTime());
	 * final int diffInHours = (int) TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	 * result += diffInHours;
	 * }
	 * return result;
	 * }
	 */

	// Relationships ----------------------------------------------------------

	/*
	 * @OneToMany
	 * protected List<Activity> activities;
	 */
}
