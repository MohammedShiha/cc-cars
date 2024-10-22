package code.climb.cccars.repository;

import code.climb.cccars.model.Listing;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ListingRepository extends CrudRepository<Listing, Integer>, JpaSpecificationExecutor<Listing> {
}
