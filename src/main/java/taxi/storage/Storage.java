package taxi.storage;

import java.util.ArrayList;
import java.util.List;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;

public class Storage {
    public static final List<Car> cars = new ArrayList<>();
    public static final List<Driver> drivers = new ArrayList<>();
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    private static long manufactureId = 0;
    private static long carId = 0;
    private static long driverId = 0;

    public static Manufacturer addManufacture(Manufacturer manufacturer) {
        manufacturer.setId(++manufactureId);
        manufacturers.add(manufacturer);
        return manufacturer;
    }

    public static Car addCar(Car car) {
        carId++;
        car.setId(++carId);
        cars.add(car);
        return car;
    }

    public static Driver addDriver(Driver driver) {
        driver.setId(++driverId);
        drivers.add(driver);
        return driver;
    }
}
