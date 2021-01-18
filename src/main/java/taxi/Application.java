package taxi;

import java.util.ArrayList;
import java.util.List;
import taxi.lib.Injector;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;
import taxi.service.CarService;
import taxi.service.DriverService;
import taxi.service.ManufacturerService;

public class Application {
    private static final Injector injector = Injector.getInstance("taxi");
    private static Driver petrovich;
    private static Driver ivan;
    private static Driver vasya;
    private static Manufacturer bmwManufacturer;
    private static Manufacturer mercedesManufacturer;
    private static Manufacturer audiManufacturer;
    private static Car x5;
    private static Car w221;
    private static Car q8;
    private static ManufacturerService manufacturerService;
    private static DriverService driverService;
    private static CarService carService;

    public static void main(String[] args) {
        petrovich = new Driver("Petrovich", "1111");
        ivan = new Driver("Ivan", "2222");
        vasya = new Driver("Vasya", "333");
        bmwManufacturer = new Manufacturer("BNW", "Germany");
        mercedesManufacturer = new Manufacturer("Mercedes", "Germany");
        audiManufacturer = new Manufacturer("Audi", "German");
        x5 = new Car("x5", bmwManufacturer);
        w221 = new Car("w221", mercedesManufacturer);
        q8 = new Car("q8", audiManufacturer);
        testDriverService();
        testCarService();
    }

    public static void testCarService() {
        manufacturerService = (ManufacturerService) injector.getInstance(ManufacturerService.class);
        driverService = (DriverService) injector.getInstance(DriverService.class);
        carService = (CarService) injector.getInstance(CarService.class);

        Manufacturer manufacturerForX5 = manufacturerService
                .create(new Manufacturer("BNW", "Germany"));
        Manufacturer manufacturerForW221 = manufacturerService
                .create(new Manufacturer("Mercedes", "Germany"));
        Manufacturer manufacturerForQ8 = manufacturerService
                .create(new Manufacturer("Audi", "Germany"));

        x5.setManufacturer(manufacturerForX5);
        w221.setManufacturer(manufacturerForW221);
        q8.setManufacturer(manufacturerForQ8);
        Car carX5 = carService.create(x5);

        List<Car> carsForTest = new ArrayList<>();
        carsForTest.addAll(List.of(x5, w221));

        System.out.println(carService.getAll());
        Car update = carService.get(carX5.getId());
        update.setModel("i6");
        carService.update(update);
        System.out.println(carService.getAll());

        Driver bobDriver = driverService.create(new Driver("Bob", "4444"));
        carService.delete(update.getId());
        System.out.println(carService.getAll());
        carService.addDriverToCar(bobDriver, x5);
        System.out.println(carService.get(x5.getId()));

        System.out.println(carService.getAllByDriver(bobDriver.getId()));
        carService.removeDriverFromCar(bobDriver, x5);
    }

    public static void testDriverService() {
        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        driverService.create(petrovich);
        driverService.create(ivan);
        driverService.create(vasya);
        Long petrovichId = petrovich.getId();
        System.out.println(driverService.getAll());
        Driver update = driverService.get(petrovichId);
        update.setName("Nicolay");
        driverService.update(update);
        System.out.println(driverService.getAll());
        driverService.delete(update.getId());
        System.out.println(driverService.getAll());
    }

    public static void testManufacturerService() {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);
        manufacturerService.create(bmwManufacturer);
        manufacturerService.create(mercedesManufacturer);
        manufacturerService.create(audiManufacturer);
        Long bmwManufacturerId = bmwManufacturer.getId();
        System.out.println(manufacturerService.getAll());
        Manufacturer update = manufacturerService.get(bmwManufacturerId);
        update.setModel("Toyota");
        manufacturerService.update(update);
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(update.getId());
        System.out.println(manufacturerService.getAll());
    }
}
