package code.climb.cccars.service;

import code.climb.cccars.model.Car;
import code.climb.cccars.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Optional<Car> getCar(int carId) {
        return carRepository.findById(carId);
    }

    public Page<Car> getCars(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return carRepository.findAll(pageable);
    }

    public void deleteCar(int carId) {
        carRepository.deleteById(carId);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> findCarsByQueryParams(Specification<Car> spec){
        return carRepository.findAll(spec);
    }

    public List<String> getMakes() {
        return carRepository.findDistinctMakes();
    }

    public List<String> getModelsForMake(String make) {
        return carRepository.findModelsForMake(make);
    }
}
