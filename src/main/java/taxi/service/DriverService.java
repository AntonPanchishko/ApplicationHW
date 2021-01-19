package taxi.service;

import java.util.List;
import taxi.model.Driver;

public interface DriverService extends GenericService<Driver, Long> {
    Driver create(Driver driver);

    Driver get(Long id);

    List<Driver> getAll();

    Driver update(Driver driver);

    boolean delete(Long id);
}
