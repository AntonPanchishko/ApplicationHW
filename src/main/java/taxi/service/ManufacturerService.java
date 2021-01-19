package taxi.service;

import java.util.List;
import taxi.model.Manufacturer;

public interface ManufacturerService extends GenericService<Manufacturer, Long> {
    Manufacturer create(Manufacturer manufacturer);

    Manufacturer get(Long id);

    List<Manufacturer> getAll();

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
