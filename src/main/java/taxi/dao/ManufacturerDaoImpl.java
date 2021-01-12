package taxi.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import taxi.lib.Dao;
import taxi.model.Manufacturer;
import taxi.storage.Storage;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacture(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.of(Storage.manufacturers.get(Math.toIntExact(id)));
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return Storage.manufacturers
                .set(Storage.manufacturers.indexOf(manufacturer), manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        return Storage.manufacturers.removeIf(i -> Objects.equals(i.getId(), id));
    }
}
