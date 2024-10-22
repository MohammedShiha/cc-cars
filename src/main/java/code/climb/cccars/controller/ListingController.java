package code.climb.cccars.controller;

import code.climb.cccars.dto.CommentDTO;
import code.climb.cccars.dto.ListingDTO;
import code.climb.cccars.model.*;
import code.climb.cccars.service.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/listing")
public class ListingController {
    private final ListingService listingService;
    private final CommentService commentService;
    private final PictureService pictureService;
    private final FileService fileService;
    private final CarService carService;

    public ListingController(ListingService listingService, CommentService commentService, PictureService pictureService, FileService fileService, CarService carService, UserService userService) {
        this.listingService = listingService;
        this.commentService = commentService;
        this.pictureService = pictureService;
        this.fileService = fileService;
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<Listing> addListing(@RequestBody ListingDTO requestBody) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getDetails();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Listing listing = new Listing();
        carService.getCar(requestBody.getCarId()).ifPresent(listing::setCar);
        listing.setUser(user);
        listing.setStatus(ListingStatus.ACTIVE);
        listing.setMileage(requestBody.getMileage());
        listing.setColor(requestBody.getColor());
        listing.setDescription(requestBody.getDescription());
        listing.setCondition(requestBody.getCondition());
        listing.setLocation(requestBody.getLocation());
        listing.setPrice(requestBody.getPrice());
        listing.setCreatedAt(Instant.now());
        listing.setUpdatedAt(Instant.now());

        Listing savedListing = listingService.addListing(listing);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedListing);
    }

    @PutMapping("/{listingId}")
    public ResponseEntity<Listing> updateListing(@PathVariable("listingId") int listingId, @RequestBody ListingDTO requestBody) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getDetails();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Listing> listing = listingService.getListingById(listingId);
        if (!listing.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Listing listingValue = listing.get();
        if (!listingValue.getUser().getUsername().equals(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        listingValue.setMileage(requestBody.getMileage());
        listingValue.setStatus(requestBody.getStatus());
        listingValue.setColor(requestBody.getColor());
        listingValue.setDescription(requestBody.getDescription());
        listingValue.setCondition(requestBody.getCondition());
        listingValue.setLocation(requestBody.getLocation());
        listingValue.setPrice(requestBody.getPrice());
        listingValue.setUpdatedAt(Instant.now());

        Listing response = listingService.updateListing(listingValue);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<Listing> getListing(@PathVariable("listingId") int listingId) {
        Optional<Listing> listing = listingService.getListingById(listingId);
        if (listing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listing.get());
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Void> deleteListing(@PathVariable("listingId") int listingId) {
        Optional<Listing> listing = listingService.getListingById(listingId);
        if (listing.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Listing listingValue = listing.get();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                User user = (User) auth.getDetails();
                if (user.getUsername().equals(listingValue.getUser().getUsername()) || user.getRole() == Role.ADMIN) {
                    listingService.deleteById(listingId);
                    return ResponseEntity.ok().build();
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Listing>> searchListings(@RequestParam String keyword) {
        Specification<Listing> spec = Specification.where((root, query, cb) -> cb.like(root.get("description"), "%" + keyword + "%"));
        spec = spec.or(Specification.where((root, query, cb) -> cb.like(root.get("location"), "%" + keyword + "%")));
        List<Listing> listings = listingService.findByQueryParams(spec);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/{listingId}/comments")
    public ResponseEntity<List<Comment>> getListingComments(@PathVariable("listingId") int listingId) {
        List<Comment> commentsForListing = commentService.getCommentsForListing(listingId);
        return ResponseEntity.ok(commentsForListing);
    }

    @PostMapping("/{listingId}/comments")
    public ResponseEntity<Comment> addListingComment(@PathVariable("listingId") int listingId, @RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getDetails();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        comment.setUser(user);
        comment.setListing(listingId);
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(Instant.now());
        comment.setUpdatedAt(Instant.now());
        Comment savedComment = commentService.addComment(comment);
        return ResponseEntity.ok(savedComment);
    }

    @PutMapping("/{listingId}/comments/{commentId}")
    public ResponseEntity<Comment> updateListingComment(@PathVariable("listingId") int listingId, @PathVariable("commentId") int commentId, @RequestBody CommentDTO commentDTO) {
        Optional<Comment> comment = commentService.getComment(commentId);
        if (comment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getDetails();
        Comment commentValue = comment.get();
        if (user == null || !commentValue.getUser().getUsername().equals(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        commentValue.setContent(commentDTO.getContent());
        commentValue.setUpdatedAt(Instant.now());
        Comment savedComment = commentService.updateComment(commentValue);
        return ResponseEntity.ok(savedComment);
    }

    @PostMapping("/{listingId}/pictures")
    public ResponseEntity<Picture> addListingPicture(@PathVariable("listingId") int listingId, @RequestParam("payload") MultipartFile file) {
        try {
            String location = fileService.storePicture(file);
            Picture picture = new Picture();
            picture.setListing(listingId);
            picture.setUrl(location);
            picture.setUploadDate(Instant.now());
            Picture saved = pictureService.save(picture);
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{listingId}/pictures")
    public ResponseEntity<List<Picture>> getListingPictures(@PathVariable("listingId") int listingId) {
        List<Picture> pictures = pictureService.getListingPictures(listingId);
        return ResponseEntity.ok(pictures);
    }
}
