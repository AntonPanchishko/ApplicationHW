package taxi.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import taxi.dao.DriverDao;
import taxi.exception.DataProcessingException;
import taxi.lib.Dao;
import taxi.model.Driver;
import taxi.util.ConnectionUtil;

@Dao
public class DriverDaoJdbcImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String query = "INSERT INTO driver (name, licence_number) VALUE (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenceNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class));
            }
            return driver;
        } catch (SQLException ex) {
            throw new DataProcessingException("Couldn't add driver into DB ", ex);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        String query = "SELECT * FROM driver WHERE driver_id = ? AND deleted = false";
        Driver driver = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                driver = createDriverFromDb(resultSet);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get driver with id = "
                    + id, ex);
        }
        return Optional.ofNullable(driver);
    }

    @Override
    public List<Driver> getAll() {
        String query = "SELECT * FROM driver WHERE deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(createDriverFromDb(resultSet));
            }
            return drivers;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all elements "
                    + "from driver DB", ex);
        }
    }

    @Override
    public Driver update(Driver driver) {
        String query = "UPDATE driver SET name = ?, licence_number = ? "
                + " WHERE driver_id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenceNumber());
            statement.setLong(3, driver.getId());
            statement.executeUpdate();
            return driver;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update "
                    + driver.getName() + " element", ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE driver SET deleted = true WHERE driver_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int deletedLines = statement.executeUpdate();
            return deletedLines > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete element with  id "
                    + id + " from DB", ex);
        }
    }

    public static Driver createDriverFromDb(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("driver_id", Long.class);
        String name = resultSet.getString("name");
        String licenseNumber = resultSet.getString("licence_number");
        Driver driver = new Driver(name, licenseNumber);
        driver.setId(id);
        return driver;
    }
}
