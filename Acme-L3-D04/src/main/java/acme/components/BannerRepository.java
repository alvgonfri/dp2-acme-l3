
package acme.components;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.jpa.repository.Query;

import acme.entities.banners.Banner;
import acme.framework.repositories.AbstractRepository;

public interface BannerRepository extends AbstractRepository {

	@Query("select count(b) from Banner b where displayStart<= :date AND displayEnd>= :date")
	int countAvailableBanners(Date date);

	@Query("select b from Banner b where displayStart<= :date AND displayEnd>= :date")
	List<Banner> findManyAvailableBanners(Date date);

	default Banner findRandomBanner() {
		Banner result;
		int count, index;
		ThreadLocalRandom random;
		List<Banner> list;

		count = this.countAvailableBanners(new Date());
		if (count == 0)
			result = null;
		else {
			random = ThreadLocalRandom.current();
			index = random.nextInt(0, count);
			list = this.findManyAvailableBanners(new Date());
			result = list.isEmpty() ? null : list.get(index);
		}

		return result;
	}

}
