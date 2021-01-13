package taxi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import taxi.dao.CarDao;
import taxi.lib.Inject;
import taxi.lib.Service;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.storage.Storage;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        return carDao.get(id);
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public Car update(Car car) {
        return carDao.create(car);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        carDao.addDriverToCar(driver, car);
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        Storage.cars.stream()
                .filter(e -> Objects.equals(e, car))
                .findFirst().get()
                .getDrivers()
                .removeIf(e -> Objects.equals(e, driver));
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        List<Car> carsByDriver = new ArrayList<>();

        for (int i = 0; i < Storage.cars.size(); i++) {
            for (int j = 0; j < Storage.cars.get(j).getDrivers().size(); j++) {
                if (Objects.equals(Storage.cars.get(j).getDrivers().get(j).getId(), driverId)) {
                    carsByDriver.add(Storage.cars.get(i));
                }
            }
        }
        return carsByDriver;
    }
}
