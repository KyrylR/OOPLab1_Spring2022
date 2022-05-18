package ua.univ.DAO;

import ua.univ.config.DataSourceConfig;
import ua.univ.models.CarDriver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDriverDAO {
    private final Connection connection;

    private CarDAO carDAO;
    private DriverDAO driverDAO;

    public CarDriverDAO() throws ClassNotFoundException, SQLException {
        connection = new DataSourceConfig().getConnection();
        carDAO = new CarDAO();
        driverDAO = new DriverDAO();
    }

    public void stop() throws SQLException {
        connection.close();
    }

    public List<CarDriver> indexCarDriver() {
        List<CarDriver> carDriverRelationsList = new ArrayList<>();

        String sql = "SELECT * FROM car_driver_relations";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int carId = resultSet.getInt("car_id");
                int driverId = resultSet.getInt("driver_id");

                carDriverRelationsList.add(new CarDriver(id,
                        carDAO.getCar(carId),
                        driverDAO.getDriver(driverId)));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println(" >>     " + e.getMessage());
        }

        return carDriverRelationsList;
    }

    public CarDriver getCarDriver(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM car_driver_relations " +
                    "WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return new CarDriver(resultSet.getInt("id"),
                        carDAO.getCar(resultSet.getInt("car_id")),
                        driverDAO.getDriver(resultSet.getInt("driver_id")));
            }

            return null;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return null;
        }
    }

    public int getMaxGlobalId() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT max(id) FROM car_driver_relations");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("max");
            }

            return -1;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return -1;
        }
    }

    public boolean saveCarDriver(CarDriver studentCourseRelation) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO car_driver_relations " +
                    "(car_id, driver_id) " +
                    "VALUES((SELECT id from cars WHERE id=?), (SELECT id from drivers WHERE id=?))");
            statement.setInt(1, studentCourseRelation.getCar().getId());
            statement.setInt(2, studentCourseRelation.getDriver().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean updateCarDriver(int id, CarDriver updatedCarDriver) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE car_driver_relations " +
                            "SET car_id=(SELECT id from cars WHERE id=?), driver_id=(SELECT id from drivers WHERE id=?)" +
                            "WHERE id=?"
            );
            statement.setInt(1, updatedCarDriver.getCar().getId());
            statement.setInt(2, updatedCarDriver.getDriver().getId());
            statement.setInt(3, id);
            int exec = statement.executeUpdate();

            if (exec > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCarDriver(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM car_driver_relations " +
                    "WHERE id = ?");
            statement.setInt(1, id);
            int exec = statement.executeUpdate();
            if (exec > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }
}
