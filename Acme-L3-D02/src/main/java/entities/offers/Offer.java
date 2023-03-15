
package entities.offers;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Offer extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	// Attributes ----------
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	protected Date				moment;

	@NotBlank
	@Length(max = 75)
	protected String			heading;

	@Length(max = 100)
	protected String			summary;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startAvalible;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endAvalible;

	@PositiveOrZero
	protected Money				price;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
