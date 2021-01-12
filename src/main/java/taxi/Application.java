package taxi;

import taxi.lib.Injector;
import taxi.model.Manufacturer;
import taxi.service.ManufacturerService;

public class Application {
    private static final Injector injector = Injector.getInstance("taxi");
    public static void main(String[] args) {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);
        Manufacturer volkswagen = new Manufacturer("BMW");
        Manufacturer audi = new Manufacturer("Audy");
        Manufacturer tesla = new Manufacturer("Mercedes");

        manufacturerService.create(volkswagen);
        manufacturerService.create(audi);
        manufacturerService.create(tesla);

        System.out.println(manufacturerService.getAll());

        Manufacturer updatedAudi = manufacturerService.get(1L);
        updatedAudi.setModel("Toyota");
        manufacturerService.update(updatedAudi);

        System.out.println(manufacturerService.getAll());

        manufacturerService.delete(1L);

        System.out.println(manufacturerService.getAll());
    }
}
