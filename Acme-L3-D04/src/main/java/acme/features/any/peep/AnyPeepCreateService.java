/*
 * AuthenticatedConsumerController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.peep;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peeps.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Peep object;
		Date moment;
		Principal principal;
		String nick;
		UserAccount userAccount;

		moment = MomentHelper.getCurrentMoment();
		principal = super.getRequest().getPrincipal();
		if (principal.isAnonymous())
			nick = "";
		else {
			userAccount = this.repository.findOneUserAccountById(principal.getAccountId());
			nick = userAccount.getIdentity().getName() + " " + userAccount.getIdentity().getSurname();
		}

		object = new Peep();
		object.setNick(nick);
		object.setMoment(moment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;

		super.bind(object, "moment", "title", "nick", "message", "email", "moreInfo");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "title", "nick", "message", "email", "moreInfo");

		super.getResponse().setData(tuple);

	}
}
