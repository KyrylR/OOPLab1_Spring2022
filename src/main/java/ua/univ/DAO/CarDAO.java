package ua.univ.DAO;

import ua.univ.config.DataSourceConfig;
import ua.univ.models.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private final Connection connection;

    public CarDAO() throws ClassNotFoundException, SQLException {
        connection = new DataSourceConfig().getConnection();
    }

    public void stop() throws SQLException {
        connection.close();
    }

    public List<Car> indexCar() {
        List<Car> carsList = new ArrayList<>();

        String sql = "SELECT * FROM cars";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Boolean isReady = resultSet.getBoolean("is_ready");
                String purpose = resultSet.getString("purpose");

                carsList.add(new Car(id, isReady, purpose));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println(" >>     " + e.getMessage());
        }

        return carsList;
    }

    public Car getCar(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cars " +
                    "WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return new Car(resultSet.getInt("id"),
                        resultSet.getBoolean("is_ready"),
                        resultSet.getString("purpose"));
            }

            return null;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return null;
        }
    }

    public int getMaxGlobalId() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT max(id) FROM cars");

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

    public boolean saveCar(Car car) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO cars " +
                    "(is_ready, purpose) " +
                    "VALUES(?, ?)");
            statement.setBoolean(1, car.isReady());
            statement.setString(2, car.getPurpose());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean updateCar(int id, Car updatedCar) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE cars " +
                            "SET is_ready=?, purpose=?" +
                            "WHERE id=?"
            );
            statement.setBoolean(1, updatedCar.isReady());
            statement.setString(2, updatedCar.getPurpose());
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

    public boolean deleteCar(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM cars " +
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
