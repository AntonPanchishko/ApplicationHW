package taxi;

import taxi.lib.Injector;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;
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
        testManufacturerService();
    }

    public static void testManufacturerService() {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);
        manufacturerService.create(bmwManufacturer);
        manufacturerService.create(mercedesManufacturer);
        manufacturerService.create(audiManufacturer);
        System.out.println(manufacturerService.getAll());
        Manufacturer updatedAudi = manufacturerService.get(3L);
        updatedAudi.setModel("Toyota");
        manufacturerService.update(updatedAudi);
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(3L);
        System.out.println(manufacturerService.getAll());
    }
}
