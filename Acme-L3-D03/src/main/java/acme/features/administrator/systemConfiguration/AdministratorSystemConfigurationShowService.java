
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.system.configuration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationShowService extends AbstractService<Authenticated, SystemConfiguration> {

	@Autowired
	protected AdministratorSystemConfigurationRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		final SystemConfiguration object;

		object = this.repository.findCurrentSystemConfiguration();

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "defaultSystemCurrency", "acceptedCurrencies");

		super.getResponse().setData(tuple);
	}

}
