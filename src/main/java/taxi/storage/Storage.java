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

    public static void addManufacture(Manufacturer manufacturer) {
        manufactureId++;
        manufacturer.setId(manufactureId);
        manufacturers.add(manufacturer);
    }
}
