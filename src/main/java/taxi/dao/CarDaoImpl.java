package taxi.dao;

import java.util.List;
import java.util.Objects;
import taxi.lib.Dao;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.storage.Storage;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Car get(Long id) {
        return Storage.cars.stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst().get();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public Car update(Car car) {
        Long currentCarId = car.getId();
        Car oldCar = get(currentCarId);
        Storage.cars.set(Storage.cars.indexOf(oldCar), car);
        return get(currentCarId);
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars.removeIf(e -> Objects.equals(e.getId(), id));
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        for (int i = 0; i < Storage.cars.size(); i++) {
            if (Objects.equals(car.getId(), Storage.cars.get(i).getId())) {
                Storage.cars.get(i).getDrivers().add(driver);
            }
        }
    }
}
