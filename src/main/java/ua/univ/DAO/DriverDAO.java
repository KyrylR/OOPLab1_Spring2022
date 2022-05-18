package ua.univ.DAO;

import ua.univ.config.DataSourceConfig;
import ua.univ.models.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {
    private final Connection connection;

    public DriverDAO() throws ClassNotFoundException, SQLException {
        connection = new DataSourceConfig().getConnection();
    }

    public void stop() throws SQLException {
        connection.close();
    }

    public List<Driver> indexDriver() {
        List<Driver> driversList = new ArrayList<>();

        String sql = "SELECT * FROM drivers";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                driversList.add(new Driver(id, name));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println(" >>     " + e.getMessage());
        }

        return driversList;
    }

    public Driver getDriver(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM drivers " +
                    "WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return new Driver(resultSet.getInt("id"),
                        resultSet.getString("name"));
            }

            return null;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return null;
        }
    }

    public int getMaxGlobalId() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT max(id) FROM drivers");

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

    public boolean saveDriver(Driver driver) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO drivers " +
                    "(name) " +
                    "VALUES(?)");
            statement.setString(1, driver.getName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean updateDriver(int id, Driver updatedDriver) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE drivers " +
                            "SET name=?" +
                            "WHERE id=?"
            );
            statement.setString(1, updatedDriver.getName());
            statement.setInt(2, id);
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

    public boolean deleteDriver(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM drivers " +
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
