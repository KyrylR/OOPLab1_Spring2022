package ua.univ.dao;

import lombok.extern.slf4j.Slf4j;
import ua.univ.config.DataSourceConfig;
import ua.univ.models.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DriverDAO {
    private static final String ID_COLUMN = "id";
    private static final String NAME = "name";
    private final Connection connection;

    public DriverDAO() throws SQLException {
        try {
            connection = new DataSourceConfig().getConnection();
        } catch (SQLException e) {
            String errorMessage = "Connection error.";
            log.error(errorMessage);
            throw new SQLException(errorMessage);
        }
    }

    public List<Driver> indexDriver() throws SQLException {
        List<Driver> driversList = new ArrayList<>();
        String sql = "SELECT * FROM drivers";

        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt(ID_COLUMN);
                String name = resultSet.getString(NAME);

                driversList.add(new Driver(id, name));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }

        return driversList;
    }

    public Driver getDriver(int id) throws SQLException {
        String sql = "SELECT * FROM drivers WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Driver(resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NAME));
            }

            return null;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    private void validate(Driver driver) throws SQLException {
        String sql1 = "SELECT id from drivers WHERE name=?";

        try (PreparedStatement ps1 = connection.prepareStatement(sql1)) {
            ps1.setString(1, driver.getName());

            ResultSet resultSetPs1 = ps1.executeQuery();
            if (resultSetPs1.next()) {
                String errorMessage = String.format("Driver with name: %s already exists!", driver.getName());
                log.error(errorMessage);
                throw new SQLException(errorMessage);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public int saveDriver(Driver driver) throws SQLException {
        String sql1 = "INSERT INTO drivers (name) VALUES(?) RETURNING id";
        String sql2 = "SELECT id FROM drivers WHERE name=?";

        try (PreparedStatement ps1 = connection.prepareStatement(sql1);
             PreparedStatement ps2 = connection.prepareStatement(sql2)) {
            connection.setAutoCommit(false);
            validate(driver);

            ps2.setString(1, driver.getName());

            ResultSet resultSet = ps2.executeQuery();

            if (!resultSet.next()) {
                ps1.setString(1, driver.getName());
                ResultSet rs = ps1.executeQuery();
                connection.commit();

                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    String errorMessage = "No id obtained while adding a new car-driver!";
                    log.error(errorMessage);
                    throw new SQLException(errorMessage);
                }
            } else {
                throw new SQLException("Car-driver exists!");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    public void updateDriver(int id, Driver updatedDriver) throws SQLException {
        String sql = "UPDATE drivers SET name=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, updatedDriver.getName());
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteDriver(int id) throws SQLException {
        String sql = "DELETE FROM drivers WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }
}
