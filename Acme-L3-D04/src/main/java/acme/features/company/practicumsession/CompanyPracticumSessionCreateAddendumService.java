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

package acme.features.company.practicumsession;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
import acme.entities.practicums.PracticumSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionCreateAddendumService extends AbstractService<Company, PracticumSession> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;

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
		PracticumSession object;

		object = new PracticumSession();
		object.setDraftMode(true);
		object.setAddendum(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("practicum", int.class);
		practicum = this.repository.findOnePracticaById(practicumId);

		super.bind(object, "title", "summary", "link", "startDate", "endDate");

		object.setPracticum(practicum);

	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		final Date startDate = super.getRequest().getData("startDate", Date.class);
		final Date endDate = super.getRequest().getData("endDate", Date.class);
		final Date availableStart = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
		final Date availableEnd = MomentHelper.deltaFromMoment(startDate, 7, ChronoUnit.DAYS);

		final boolean validStart = startDate.getTime() >= availableStart.getTime();
		super.state(validStart, "startDate", "company.practicum-session.validation.startDate.error.AtLeastOneWeekAntiquity");

		final boolean validEnd = endDate.getTime() >= availableEnd.getTime();
		super.state(validEnd, "endDate", "company.practicum-session.validation.endDate.error.AtLeastOneWeekDuration");

		boolean confirmation;
		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "company.practicum-session.validation.confirmation");

		final Collection<Practicum> practica;
		Collection<PracticumSession> addendums;
		final SelectChoices choices;
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();

		practica = this.repository.findManyPublishedPracticaByCompanyId(companyId);
		choices = SelectChoices.from(practica, "code", object.getPracticum());

		final int selectedId = Integer.parseInt(choices.getSelected().getKey());
		addendums = this.repository.findAddendumSessionsByPracticumId(selectedId);

		final boolean valid = addendums.size() == 0;
		super.state(valid, "practicum", "company.practicum-session.validation.practicum.error.AddendumUsed");
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		final Collection<Practicum> practica;
		final SelectChoices choices;
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();

		practica = this.repository.findManyPublishedPracticaByCompanyId(companyId);
		choices = SelectChoices.from(practica, "code", object.getPracticum());
		Tuple tuple;

		tuple = super.unbind(object, "title", "summary", "startDate", "endDate", "draftMode", "addendum", "link");
		tuple.put("practicum", choices.getSelected().getKey());
		tuple.put("practica", choices);
		tuple.put("confirmation", false);

		super.getResponse().setData(tuple);
	}
}
