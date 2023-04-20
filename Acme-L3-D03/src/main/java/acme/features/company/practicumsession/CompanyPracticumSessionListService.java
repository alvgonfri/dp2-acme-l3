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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
import acme.entities.practicums.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionListService extends AbstractService<Company, PracticumSession> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("practicumId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		final int practicumId;
		final Practicum practicum;

		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findOnePracticaById(practicumId);
		status = practicum != null && super.getRequest().getPrincipal().getActiveRoleId() == practicum.getCompany().getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<PracticumSession> object;
		int practicumId;

		practicumId = super.getRequest().getData("practicumId", int.class);
		object = this.repository.findManySessionsByPracticumId(practicumId);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		int practicumId;
		Practicum practicum;
		final boolean showCreate;
		final boolean draftMode;
		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findOnePracticaById(practicumId);
		showCreate = practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		draftMode = practicum.isDraftMode();

		Tuple tuple;

		tuple = super.unbind(object, "title", "summary", "startDate", "endDate", "link", "draftMode", "addendum");
		tuple.put("showCreate", showCreate);
		tuple.put("draftMode", draftMode);

		super.getResponse().setData(tuple);
	}
}
