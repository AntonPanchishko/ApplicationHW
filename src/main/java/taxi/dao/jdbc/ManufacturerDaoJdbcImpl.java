package taxi.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import taxi.dao.ManufacturerDao;
import taxi.exception.DataProcessingException;
import taxi.lib.Dao;
import taxi.model.Manufacturer;
import taxi.util.ConnectionUtil;

@Dao
public class ManufacturerDaoJdbcImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturer (name, country) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manufacturer.getModel());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject(1, Long.class));
            }
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Couldn't add manufacturer into DB ", ex);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturer "
                + "WHERE manufacturer_id = ? and deleted = false ";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = createManufacturerFromDb(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get manufacturer with id =  "
                    + id, ex);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturer";
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(createManufacturerFromDb(resultSet));
            }
            return manufacturerList;

        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all elements "
                    + "from manufacturer DB", ex);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturer "
                + "SET name = ?,country = ? "
                + "WHERE manufacturer_id = ? and deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query)) {
            preparedStatement.setString(1, manufacturer.getModel());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update "
                    + manufacturer.getModel() + " element", ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturer SET deleted = true WHERE manufacturer_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete element with  id "
                    + id + " from DB", ex);
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
}
