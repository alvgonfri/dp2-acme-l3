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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicums.Practicum;
import acme.entities.practicums.PracticumSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("Select s from PracticumSession s Where s.id = :id")
	PracticumSession findOneSessionById(int id);

	@Query("Select s From PracticumSession s Where s.practicum.id = :id")
	Collection<PracticumSession> findManySessionsByPracticumId(int id);

	@Query("Select s From PracticumSession s Where s.practicum.company.id = :id")
	Collection<PracticumSession> findManySessionsByCompanyId(int id);

	@Query("Select s From PracticumSession s Where s.practicum.id = :id And s.addendum = true")
	Collection<PracticumSession> findAddendumSessionsByPracticumId(int id);

	@Query("Select p From Practicum p Where p.company.id = :id")
	Collection<Practicum> findManyPracticaByCompanyId(int id);

	@Query("Select p From Practicum p Where p.company.id = :id And p.draftMode = true")
	Collection<Practicum> findManyPrivatePracticaByCompanyId(int id);

	@Query("Select p From Practicum p Where p.company.id = :id And p.draftMode = false")
	Collection<Practicum> findManyPublishedPracticaByCompanyId(int id);

	@Query("Select p From Practicum p Where p.id = :id")
	Practicum findOnePracticaById(int id);

	@Query("Select s.practicum From PracticumSession s Where s.id = :id")
	Practicum findOnePracticumBySessionId(int id);

	@Query("Select p From Practicum p where p.draftMode = false")
	Collection<Practicum> findPublishedPractica();

	@Query("Select p From Practicum p where p.draftMode = true")
	Collection<Practicum> findPrivatePractica();

	@Query("Select c From Company c Where c.id = :id")
	Company findOneCompanyById(int id);

	@Query("Select p From Practicum p")
	Collection<Practicum> findAllPractica();
}
