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

package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicums.Practicum;
import acme.entities.practicums.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.course.id = :masterId")
	Collection<Practicum> findPracticumByCourseId(int masterId);

	@Query("select p from Practicum p where p.id = :id")
	Practicum findPracticumById(int id);

	@Query("Select s from PracticumSession s where s.practicum.id = :id")
	Collection<PracticumSession> findSessionsByPracticum(int id);

}
