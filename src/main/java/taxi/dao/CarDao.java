package taxi.dao;

import java.util.List;
import java.util.Optional;
import taxi.model.Car;
import taxi.model.Driver;

public interface CarDao {
    Car create(Car car);

    Optional<Car> get(Long id);

    List<Car> getAll();

    Car update(Car car);

    boolean delete(Long id);

    void addDriverToCar(Driver driver, Car car);

    List<Car> getAllByDriver(Long driverId);
}
