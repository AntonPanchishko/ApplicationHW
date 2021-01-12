package taxi.model;

import java.util.List;
import java.util.Objects;

public class Manufacturer {
    private long id;
    private String model;
    private List<Driver> drivers;

    public Manufacturer(String model) {
        this.model = model;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Manufacturer that = (Manufacturer) o;
        return id == that.id && Objects.equals(model, that.model)
                && Objects.equals(drivers, that.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, drivers);
    }

    @Override
    public String toString() {
        return "Manufacturer{" + "id=" + id + ", taxi.model='" + model + '\''
                + ", drivers=" + drivers + '}';
    }
}
