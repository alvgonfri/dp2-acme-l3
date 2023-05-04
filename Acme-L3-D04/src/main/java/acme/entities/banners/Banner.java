
package acme.entities.banners;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				displayStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				displayEnd;

	@NotBlank
	@URL
	protected String			picture;

	@NotBlank
	@Length(max = 75)
	protected String			slogan;

	@NotBlank
	@URL
	protected String			target;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
