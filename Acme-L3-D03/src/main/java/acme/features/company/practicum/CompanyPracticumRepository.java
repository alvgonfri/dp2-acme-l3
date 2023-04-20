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

package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.practicums.Practicum;
import acme.entities.practicums.PracticumSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("select a from Company a where a.id = ?1")
	Company findCompanyById(int activeRoleId);

	@Query("select a from Practicum a where a.id = ?1")
	Practicum findPracticumById(int masterId);

	@Query("select sp from SessionPracticum sp where sp.practicum.id = ?1")
	Collection<PracticumSession> findPracticumSessionsByPracticumId(int id);

	@Query("select a from Practicum a where a.company.id = ?1")
	Collection<Practicum> findPracticumsByCompanyId(int activeRoleId);

	@Query("select a from Practicum a where a.draftMode = false")
	Collection<Practicum> findPracticums();

	@Query("select a from Course a where a.id = ?1")
	Course findCourseById(int courseId);

	@Query("select a from Course a")
	Collection<Course> findAllCourses();

	@Query("select p from Practicum p where p.code = ?1")
	Collection<Practicum> findPracticumByCode(String code);
}
