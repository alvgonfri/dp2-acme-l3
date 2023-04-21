
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionRepository extends AbstractRepository {

	@Query("select a from TutorialSession a where a.id = :id")
	TutorialSession findOneTutorialSessionById(int id);

	@Query("select e from Tutorial e where e.id = :tutorialId")
	Tutorial findOneTutorialById(int tutorialId);

	@Query("select a from TutorialSession a where a.tutorial.id = :tutorialSessionId")
	Collection<TutorialSession> findManyTutorialSessionByTutorialId(int tutorialSessionId);

	@Query("select a.tutorial from TutorialSession a where a.id = :tutorialSessionId")
	Tutorial findOneTutorialByTutorialSessionId(int tutorialSessionId);

}
