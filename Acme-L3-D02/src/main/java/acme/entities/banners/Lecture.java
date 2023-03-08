
package acme.entities.banners;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
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
	@Min(0)
	protected Integer			learningTime;

	@NotBlank
	@Length(max = 100)
	protected String			body;

	@URL
	protected String			moreInfo;

	// Derived attributes -----------------------------------------------------

	@NotNull
	protected LectureType		lectureType;

	// Relationships ----------------------------------------------------------

	@ManyToOne()
	@Valid
	@NotNull
	protected Course			course;

}
