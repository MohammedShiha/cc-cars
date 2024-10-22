package code.climb.cccars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", nullable = false)
    private Integer id;

    @NotNull(message = "Car make can't be null")
    @Column(name = "make", nullable = false, length = 20)
    private String make;

    @NotNull
    @Column(name = "model", nullable = false, length = 30)
    private String model;

    @NotNull
    @Min(value = 1900, message = "Car model should be > 1900")
    @Max(value = 2024, message = "Car model should be < 2024")
    @Column(name = "model_year", nullable = false)
    private Short modelYear;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private CarFuel fuelType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gear", nullable = false)
    private CarGear gear;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "body_type", nullable = false)
    private CarBodyType bodyType;

    @NotNull
    @Positive(message = "Engine Power should be > 0")
    @Column(name = "engine_power", nullable = false)
    private Short enginePower;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "wheel_drive", nullable = false)
    private CarWheelDrive wheelDrive;

    @NotNull
    @Min(2)
    @Max(10)
    @Column(name = "seats", nullable = false)
    private Short seats;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Short getModelYear() {
        return modelYear;
    }

    public void setModelYear(Short modelYear) {
        this.modelYear = modelYear;
    }

    public CarFuel getFuelType() {
        return fuelType;
    }

    public void setFuelType(CarFuel fuelType) {
        this.fuelType = fuelType;
    }

    public CarGear getGear() {
        return gear;
    }

    public void setGear(CarGear gear) {
        this.gear = gear;
    }

    public CarBodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(CarBodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Short getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Short enginePower) {
        this.enginePower = enginePower;
    }

    public CarWheelDrive getWheelDrive() {
        return wheelDrive;
    }

    public void setWheelDrive(CarWheelDrive wheelDrive) {
        this.wheelDrive = wheelDrive;
    }

    public Short getSeats() {
        return seats;
    }

    public void setSeats(Short seats) {
        this.seats = seats;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}