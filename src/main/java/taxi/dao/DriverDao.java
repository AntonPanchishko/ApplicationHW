package taxi.dao;

import taxi.model.Driver;

import java.util.Optional;

public interface DriverDao extends GenericDao<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
