
package acme.entities.sessions;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TutorialSession {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank
	@Length(max = 75)
	protected String				title;

	@NotBlank
	@Length(max = 100)
	protected String				summary;

	@NotNull
	protected TutorialSessionType	sessionType;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date					startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date					endDate;

	@URL
	protected String				moreInfo;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	protected Tutorial				tutorial;
}
