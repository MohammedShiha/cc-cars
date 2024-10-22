package code.climb.cccars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "listing_id", nullable = false)
    private Integer listing;

    @NotNull
    @Column(name = "url", nullable = false, length = 250)
    private String url;

    @NotNull
    @Column(name = "upload_date", nullable = false)
    private Instant uploadDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getListing() {
        return listing;
    }

    public void setListing(Integer listing) {
        this.listing = listing;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Instant uploadDate) {
        this.uploadDate = uploadDate;
    }

}