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

	@Query("Select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select p from Practicum p where p.code = :code")
	Practicum findPracticumByCode(String code);

	@Query("Select p from Practicum p where p.course.id = :id")
	Collection<Practicum> findPracticumByCourseId(int id);

	@Query("Select p from Practicum p where p.company.id = :id")
	Collection<Practicum> findPracticumByCompanyId(int id);

	@Query("Select ps from PracticumSession ps where ps.practicum.id = :id")
	Collection<PracticumSession> findPracticumSessionsByPracticumId(int id);

	@Query("Select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("Select c from Course c")
	Collection<Course> findAllCourses();

	@Query("Select p.course from Practicum p where p.id = :id")
	Course findOneCourseByPracticumId(int id);

	@Query("Select c from Company c where c.id = :id")
	Company findOneCompanyById(int id);
}
