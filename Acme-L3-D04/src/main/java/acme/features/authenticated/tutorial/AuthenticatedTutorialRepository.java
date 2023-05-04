
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedTutorialRepository extends AbstractRepository {

	@Query("select p from Tutorial p where p.course.id = :masterId")
	Collection<Tutorial> findTutorialByCourseId(int masterId);

	@Query("select p from Tutorial p where p.id = :id")
	Tutorial findTutorialById(int id);

	@Query("Select s from TutorialSession s where s.tutorial.id = :id")
	Collection<TutorialSession> findSessionsByTutorial(int id);

}
