package taxi.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import taxi.lib.Dao;
import taxi.model.Driver;
import taxi.storage.Storage;

@Dao
public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return driver;
    }

    @Override
    public Optional<Driver> get(Long id) {
        return Storage.drivers.stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst();
    }

    @Override
    public List<Driver> getAll() {
        return Storage.drivers;
    }

    @Override
    public Driver update(Driver driver) {
        Long currentDriverId = driver.getId();
        Driver oldDriver = get(driver.getId()).get();
        Storage.drivers.set(Storage.drivers.indexOf(oldDriver), driver);
        return get(currentDriverId).get();
    }

    @Override
    public boolean delete(Long id) {
        return Storage.drivers.removeIf(e -> Objects.equals(e.getId(), e));
    }
}
