package taxi.model;

import java.util.Objects;

public class Manufacturer {
    private long id;
    private String model;
    private String country;

    public Manufacturer(String model, String country) {
        this.model = model;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
                && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, country);
    }

    @Override
    public String toString() {
        return "Manufacturer{" + "id=" + id + ", model='"
                + model + '\'' + ", country='" + country + '\'' + '}';
    }
}
