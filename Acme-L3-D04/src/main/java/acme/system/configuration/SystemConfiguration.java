
package acme.system.configuration;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				defaultSystemCurrency;

	@NotBlank
	private String				acceptedCurrencies;

}
