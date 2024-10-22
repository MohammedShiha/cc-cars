package code.climb.cccars.repository;

import code.climb.cccars.model.Picture;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PictureRepository extends CrudRepository<Picture, Integer> {
    List<Picture> findByListing(int listing);
}
