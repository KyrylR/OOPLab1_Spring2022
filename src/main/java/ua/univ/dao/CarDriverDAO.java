package ua.univ.dao;

import lombok.extern.slf4j.Slf4j;
import ua.univ.config.DataSourceConfig;
import ua.univ.models.CarDriver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CarDriverDAO {
    private static final String ID = "id";
    private static final String CAR_ID = "car_id";
    private static final String DRIVER_ID = "driver_id";
    private final Connection connection;
    private final CarDAO carDAO;
    private final DriverDAO driverDAO;

    public CarDriverDAO() throws SQLException {
        try {
            connection = new DataSourceConfig().getConnection();
        } catch (SQLException e) {
            String errorMessage = "Connection error.";
            log.error(errorMessage);
            throw new SQLException(errorMessage);
        }
        carDAO = new CarDAO();
        driverDAO = new DriverDAO();
    }

    private void validate(CarDriver carDriver) throws SQLException {
        String sql1 = "SELECT id from cars WHERE id=?";
        String sql2 = "SELECT id from drivers WHERE id=?";

        try (PreparedStatement ps1 = connection.prepareStatement(sql1);
             PreparedStatement ps2 = connection.prepareStatement(sql2)) {
            ps1.setLong(1, carDriver.getCarId());
            ps2.setLong(1, carDriver.getDriverId());

            ResultSet resultSetPs1 = ps1.executeQuery();
            ResultSet resultSetPs2 = ps2.executeQuery();

            if (!resultSetPs1.next()) {
                String errorMessage = String.format("Could not find a Car with id: %d", carDriver.getCarId());
                log.error(errorMessage);
                throw new SQLException(errorMessage);
            }

            if (!resultSetPs2.next()) {
                String errorMessage = String.format("Could not find a Driver with id: %d", carDriver.getDriverId());
                log.error(errorMessage);
                throw new SQLException(errorMessage);
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public List<CarDriver> indexCarDriver() throws SQLException {
        List<CarDriver> carDriverRelationsList = new ArrayList<>();
        String sql = "SELECT * FROM car_driver_relations";

        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                int carId = resultSet.getInt(CAR_ID);
                int driverId = resultSet.getInt(DRIVER_ID);

                carDriverRelationsList.add(new CarDriver(id,
                        carDAO.getCar(carId),
                        driverDAO.getDriver(driverId)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }

        return carDriverRelationsList;
    }

    public CarDriver getCarDriver(int id) throws SQLException {
        String sql = "SELECT * FROM car_driver_relations WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new CarDriver(resultSet.getInt(ID),
                        carDAO.getCar(resultSet.getInt(CAR_ID)),
                        driverDAO.getDriver(resultSet.getInt(DRIVER_ID)));
            }

            return null;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    public int saveCarDriver(CarDriver carDriver) throws SQLException {
        String sql1 = "INSERT INTO car_driver_relations (car_id, driver_id) VALUES (?, ?) RETURNING id";
        String sql2 = "SELECT id FROM car_driver_relations WHERE car_id=? AND driver_id=?";

        try (PreparedStatement ps1 = connection.prepareStatement(sql1);
             PreparedStatement ps2 = connection.prepareStatement(sql2)) {
            connection.setAutoCommit(false);
            validate(carDriver);

            ps2.setLong(1, carDriver.getCarId());
            ps2.setLong(2, carDriver.getDriverId());

            ResultSet resultSet = ps2.executeQuery();

            if (!resultSet.next()) {
                ps1.setLong(1, carDriver.getCarId());
                ps1.setLong(2, carDriver.getDriverId());
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

    public void updateCarDriver(int id, CarDriver updatedCarDriver) throws SQLException {
        String sql1 = "SELECT id FROM car_driver_relations WHERE car_id=? AND driver_id=?";
        String sql2 = "UPDATE car_driver_relations SET car_id=?, driver_id=? WHERE id=?";


        try (PreparedStatement ps1 = connection.prepareStatement(sql1);
             PreparedStatement ps2 = connection.prepareStatement(sql2)) {
            connection.setAutoCommit(false);
            validate(updatedCarDriver);

            ps1.setLong(1, updatedCarDriver.getCarId());
            ps1.setLong(2, updatedCarDriver.getDriverId());
            ResultSet resultSet = ps1.executeQuery();

            if (!resultSet.next()) {
                ps2.setLong(1, updatedCarDriver.getCarId());
                ps2.setLong(2, updatedCarDriver.getDriverId());
                ps2.setLong(3, id);
                int affectedRows = ps2.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Updating car-driver failed, no rows affected.");
                }
                connection.commit();
            } else {
                throw new SQLException("Car-driver exists!");
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }


    public void deleteCarDriver(int id) throws SQLException {
        String sql = "DELETE FROM car_driver_relations WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }
}
