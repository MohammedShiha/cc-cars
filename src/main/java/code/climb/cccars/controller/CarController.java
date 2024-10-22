package code.climb.cccars.controller;

import code.climb.cccars.model.Car;
import code.climb.cccars.model.CarBodyType;
import code.climb.cccars.model.CarGear;
import code.climb.cccars.model.CarWheelDrive;
import code.climb.cccars.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<Page<Car>> getCarsPage(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        Page<Car> products = carService.getCars(pageNo, pageSize);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCar(@PathVariable("carId") int carId) {
        Optional<Car> car = carService.getCar(carId);
        if (car.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car.get());
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable("carId") int carId) {
        carService.deleteCar(carId);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable("carId") int carId, @RequestBody Car car) {
        Car savedCar = carService.saveCar(car);
        return ResponseEntity.ok(savedCar);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car savedCar = carService.saveCar(car);
        return ResponseEntity.ok(savedCar);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Car>> filterCars(@RequestParam(required = false) String make,
                                                 @RequestParam(required = false) String model,
                                                 @RequestParam(required = false) Short modelYear,
                                                 @RequestParam(required = false) CarGear gear,
                                                 @RequestParam(required = false) CarBodyType bodyType,
                                                 @RequestParam(required = false) Short enginePower,
                                                 @RequestParam(required = false) CarWheelDrive wheelDrive,
                                                 @RequestParam(required = false) Short seats) {

        Specification<Car> spec = Specification.where(null);

        if (make != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("make"), make));
        }

        if (model != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("model"), model));
        }

        if (modelYear != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("modelYear"), modelYear));
        }

        if (gear != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("gear"), gear));
        }

        if (bodyType != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("bodyType"), bodyType));
        }

        if (enginePower != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("enginePower"), enginePower));
        }

        if (wheelDrive != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("wheelDrive"), wheelDrive));
        }

        if (seats != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("seats"), seats));
        }

        List<Car> users = carService.findCarsByQueryParams(spec);

        return ResponseEntity.ok(users);
    }

    @GetMapping("/makes")
    public ResponseEntity<List<String>> getMakes() {
        return ResponseEntity.ok(carService.getMakes());
    }

    @GetMapping("/models")
    public ResponseEntity<List<String>> getModels(@RequestParam(name = "make") String make) {
        return ResponseEntity.ok(carService.getModelsForMake(make));
    }
}
