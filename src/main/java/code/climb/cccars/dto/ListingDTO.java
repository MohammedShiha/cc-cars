package code.climb.cccars.dto;

import code.climb.cccars.model.*;
import jakarta.validation.constraints.NotNull;

public class ListingDTO {
    @NotNull
    private Integer carId;

    @NotNull
    private ListingStatus status;

    @NotNull
    private Integer mileage;

    @NotNull
    private String color;

    private String description;

    @NotNull
    private ListingCondition condition;

    private String location;

    @NotNull
    private Integer price;

    public @NotNull Integer getCarId() {
        return carId;
    }

    public @NotNull ListingStatus getStatus() {
        return status;
    }

    public @NotNull Integer getMileage() {
        return mileage;
    }

    public @NotNull String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public @NotNull ListingCondition getCondition() {
        return condition;
    }

    public String getLocation() {
        return location;
    }

    public @NotNull Integer getPrice() {
        return price;
    }
}
