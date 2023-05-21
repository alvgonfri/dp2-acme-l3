
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialTestRepository extends AbstractRepository {

	@Query("select j from Tutorial j where j.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorialByAssistantUsername(String username);

}
