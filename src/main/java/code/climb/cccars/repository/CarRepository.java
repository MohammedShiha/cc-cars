package code.climb.cccars.repository;

import code.climb.cccars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer>, JpaSpecificationExecutor<Car> {
    @Query("SELECT DISTINCT c.make FROM Car c ORDER BY c.make ASC")
    List<String> findDistinctMakes();

    @Query("SELECT DISTINCT c.model FROM Car c WHERE c.make = :make ORDER BY c.model ASC")
    List<String> findModelsForMake(@Param("make") String make);
}
