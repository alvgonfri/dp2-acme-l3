
package acme.entities.courses;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	@NotNull
	@Min(1)
	protected int				learningTime;

	@NotBlank
	@Length(max = 100)
	protected String			body;

	@URL
	protected String			moreInfo;

	@NotNull
	protected LectureType		lectureType;

	@NotNull
	protected Boolean			draftMode;


	public Boolean isDraftMode() {
		return this.draftMode;
	}

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------


	@ManyToOne(optional = false)
	@Valid
	protected Lecturer lecturer;

}
