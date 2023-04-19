
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.system.configuration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationUpdateService extends AbstractService<Administrator, SystemConfiguration> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorSystemConfigurationRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		SystemConfiguration object;

		object = this.repository.findCurrentSystemConfiguration();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SystemConfiguration object) {
		assert object != null;

		super.bind(object, "defaultSystemCurrency", "acceptedCurrencies");
	}

	@Override
	public void validate(final SystemConfiguration object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("defaultSystemCurrency")) {

			String defaultSystemCurrency;
			String acceptedCurrencies;
			defaultSystemCurrency = super.getRequest().getData("defaultSystemCurrency", String.class);
			acceptedCurrencies = super.getRequest().getData("acceptedCurrencies", String.class);

			super.state(acceptedCurrencies.contains(defaultSystemCurrency), "defaultSystemCurrency", "administrator.system-configuration.form.error.defaultSystemCurrency");
		}

	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "defaultSystemCurrency", "acceptedCurrencies");
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
