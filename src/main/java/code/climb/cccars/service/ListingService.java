package code.climb.cccars.service;

import code.climb.cccars.model.Listing;
import code.climb.cccars.repository.ListingRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {
    private final ListingRepository repository;

    public ListingService(ListingRepository repository) {
        this.repository = repository;
    }

    public Listing addListing(Listing listing) {
        return repository.save(listing);
    }

    public Optional<Listing> getListingById(int id) {
        return repository.findById(id);
    }

    public List<Listing> findByQueryParams(Specification<Listing> spec) {
        return repository.findAll(spec);
    }

    public void deleteById(int listingId) {
        repository.deleteById(listingId);
    }

    public Listing updateListing(Listing listing) {
        return repository.save(listing);
    }
}
