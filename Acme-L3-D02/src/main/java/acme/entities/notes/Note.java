
package acme.entities.notes;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.UserIdentity;
import acme.framework.components.accounts.Principal;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Note extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				moment;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			message;

	@Email
	protected String			email;

	@URL
	protected String			moreInfo;

	// Derived attributes -----------------------------------------------------


	//provisional, falta saber como obtener realmente datos de Principal y UserIdentity
	@NotBlank
	@Length(max = 75)
	public String getAuthor(final Principal user, final UserIdentity userId) {

		return String.format("%s - %s, %s", user.getUsername(), userId.getName(), userId.getSurname());

	}
	// Relationships ----------------------------------------------------------

}
