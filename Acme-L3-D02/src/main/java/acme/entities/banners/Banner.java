
package acme.entities.banners;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				moment;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				displayStart;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				displayEnd;

	@URL
	protected String			picture;

	@NotBlank
	@Length(max = 76)
	protected String			slogan;

	@URL
	protected String			target;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
