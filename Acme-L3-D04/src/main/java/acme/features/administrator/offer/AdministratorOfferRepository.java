
package acme.features.administrator.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.offers.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("select u from Offer u where u.id = :id")
	Offer findOneOfferById(int id);

	@Query("select a from Offer a ")
	Collection<Offer> findAllOffers();

}
