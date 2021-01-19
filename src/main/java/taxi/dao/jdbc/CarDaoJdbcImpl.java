package taxi.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import taxi.dao.CarDao;
import taxi.exception.DataProcessingException;
import taxi.lib.Dao;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;
import taxi.util.ConnectionUtil;

@Dao
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String query = "INSERT INTO car(manufacturer_id, model) VALUE (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, car.getManufacturer().getId());
            statement.setString(2, car.getModel());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
            return car;
        } catch (SQLException ex) {
            throw new DataProcessingException("Couldn't add car into DB " + car, ex);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String query = "SELECT c.car_id, c.model, c.manufacturer_id, "
                + "m.name, m.country "
                + "FROM car c "
                + "INNER JOIN manufacturer m "
                + "ON c.manufacturer_id = m.manufacturer_id "
                + "WHERE c.car_id = ? AND c.deleted = false ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Car car = null;
            if (resultSet.next()) {
                car = createCarFromDb(resultSet);
                car.setDrivers(getDriversForCar(id, connection));
            }
            return Optional.ofNullable(car);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get car with id = "
                    + id, ex);
        }
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT c.car_id, c.manufacturer_id, c.model, m.name, m.country "
                + "FROM car c "
                + "INNER JOIN manufacturer m "
                + "ON c.manufacturer_id = m.manufacturer_id "
                + "WHERE c.deleted = false ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                Car currentCar = createCarFromDb(resultSet);
                currentCar.setDrivers(getDriversForCar(currentCar.getId(), connection));
                cars.add(currentCar);
            }
            return cars;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all elements "
                    + "from car DB", ex);
        }
    }

    @Override
    public Car update(Car car) {
        String queryForCars = "UPDATE car "
                + "SET manufacturer_id = ?, model = ? "
                + "WHERE car_id = ? AND deleted = false ";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement statementForCars = connection.prepareStatement(queryForCars)) {
            statementForCars.setLong(1, car.getManufacturer().getId());
            statementForCars.setString(2, car.getModel());
            statementForCars.setLong(3, car.getId());
            statementForCars.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update car " + car.getModel(), ex);
        }
        deleteObjectInCarDriverById(car.getId());

        try (Connection connection = ConnectionUtil.getConnection()) {
            for (int i = 0; i < car.getDrivers().size(); i++) {
                insertNewDriver(car.getDrivers().get(i).getId(), car.getId(), connection);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't insert car_id into DB car_driver " + car, ex);
        }

        return car;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE car SET deleted = true WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete element with id "
                    + id + " from DB", ex);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String query = "SELECT cd.car_id, model, c.manufacturer_id, manufacturer_name, origin "
                + "FROM cars_drivers cd "
                + "INNER JOIN cars c ON cd.car_id = c.car_id "
                + "INNER JOIN drivers d ON d.driver_id = cd.driver_id "
                + "WHERE cd.driver_id = ? AND d.deleted = FALSE AND c.deleted = FALSE ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(createCarFromDb(resultSet));
            }
            return cars;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get list car by driver id = "
                    + driverId, ex);
        }
    }

    private Car createCarFromDb(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("car_id", Long.class);
        Manufacturer manufacturer = createManufacturerFromDb(resultSet);
        String model = resultSet.getObject("model", String.class);
        Car car = new Car(model, manufacturer);
        car.setId(id);
        return car;
    }

    private List<Driver> getDriversForCar(Long carId, Connection connection) {
        String query = "SELECT cd.driver_id, d.name, d.licence_number "
                + "FROM car_driver cd "
                + "INNER JOIN driver d "
                + "ON d.driver_id = cd.driver_id "
                + "WHERE cd.car_id = ? AND d.deleted = FALSE";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(createDriverFromDb(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("We can't get drivers for this car", e);
        }
    }

    private void insertNewDriver(Long driverId, Long carId, Connection connection) {
        String queryForInsert = "INSERT INTO car_driver (driver_id, car_id) "
                + "VALUES (?, ?) ";
        try (PreparedStatement statementForInsert =
                        connection.prepareStatement(queryForInsert)) {
            statementForInsert.setLong(1, driverId);
            statementForInsert.setLong(2, carId);
            statementForInsert.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Cant insert into car_driver db", ex);
        }
    }

    private void deleteObjectInCarDriverById(Long id) {
        String queryForDeleteDriver = "DELETE "
                + "FROM car_driver "
                + " WHERE car_id = ? ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryForDeleteDriver)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete elements by id "
                    + id, ex);
        }
    }

    private Manufacturer createManufacturerFromDb(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("manufacturer_id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String country = resultSet.getObject("country", String.class);
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(id);
        return manufacturer;
    }

    private Driver createDriverFromDb(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("driver_id", Long.class);
        String name = resultSet.getString("name");
        String licenseNumber = resultSet.getString("licence_number");
        Driver driver = new Driver(name, licenseNumber);
        driver.setId(id);
        return driver;
    }
}
