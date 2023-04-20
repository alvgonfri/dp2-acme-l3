
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.enrolments.Enrolment;
import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("select u from Tutorial u where u.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("select u from Assistant u where u.id = :id")
	Assistant findOneAssistantById(int id);

	@Query("select a from Tutorial a where a.assistant.id = :assistantId ")
	Collection<Tutorial> findManyTutorialsByAssistantId(int assistantId);

	@Query("select c from Course c where c.draftMode = 0")
	Collection<Course> findPublishedCourses();

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select e from Tutorial e where e.code = :code")
	Enrolment findOneTutorialByCode(String code);

	@Query("select a from TutorialSession a where a.tutorial.id = :tutorialId")
	Collection<TutorialSession> findTutorialSessionByTutorialId(int tutorialId);
}
