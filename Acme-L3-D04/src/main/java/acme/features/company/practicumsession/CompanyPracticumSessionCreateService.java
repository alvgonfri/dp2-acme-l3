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
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {

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
		object.setAddendum(false);

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

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		final Collection<Practicum> practica;
		final SelectChoices choices;
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();

		practica = this.repository.findManyPrivatePracticaByCompanyId(companyId);
		choices = SelectChoices.from(practica, "code", object.getPracticum());
		Tuple tuple;

		tuple = super.unbind(object, "title", "summary", "startDate", "endDate", "draftMode", "addendum", "link");
		tuple.put("practicum", choices.getSelected().getKey());
		tuple.put("practica", choices);

		super.getResponse().setData(tuple);
	}
}
