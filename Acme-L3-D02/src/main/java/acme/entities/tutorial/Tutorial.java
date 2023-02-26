
package acme.entities.tutorial;

import java.util.Date;

import javax.persistence.Entity;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tutorial extends AbstractEntity{

	// Serialisation identifier -----------------------------------------------

		protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	protected String code; 
	
	
	@NotBlank
	@Length(max= 75)
	protected String title; 
	
	@NotBlank
	@Length(max = 100)
	protected String anAbstract; 
	´
	
	@NotBlank
	@Length(max = 100)
	protected String goals; 
	
	
	
	
	
	
	
	
	
	
	
	
	
}
