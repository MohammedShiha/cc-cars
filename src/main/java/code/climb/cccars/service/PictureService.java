package code.climb.cccars.service;

import code.climb.cccars.model.Picture;
import code.climb.cccars.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureService {
    private final PictureRepository pictureRepository;

    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<Picture> getListingPictures(int listingId) {
        return pictureRepository.findByListing(listingId);
    }

    public Picture save(Picture picture) {
        return pictureRepository.save(picture);
    }
}
