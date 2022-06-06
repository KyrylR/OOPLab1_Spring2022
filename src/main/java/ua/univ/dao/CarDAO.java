package ua.univ.dao;

import lombok.extern.slf4j.Slf4j;
import ua.univ.config.DataSourceConfig;
import ua.univ.models.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CarDAO {
    private static final String IS_READY = "is_ready";
    private static final String PURPOSE = "purpose";
    private final Connection connection;

    public CarDAO() throws SQLException {
        try {
            connection = new DataSourceConfig().getConnection();
        } catch (SQLException e) {
            String errorMessage = "Connection error.";
            log.error(errorMessage);
            throw new SQLException(errorMessage);
        }
    }

    public List<Car> indexCar() throws SQLException {
        List<Car> carsList = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                boolean isReady = resultSet.getBoolean(IS_READY);
                String purpose = resultSet.getString(PURPOSE);

                carsList.add(new Car(id, isReady, purpose));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }

        return carsList;
    }

    public Car getCar(int id) throws SQLException {
        String sql = "SELECT * FROM cars WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Car(resultSet.getInt("id"),
                        resultSet.getBoolean(IS_READY),
                        resultSet.getString(PURPOSE));
            }

            return null;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    public int saveCar(Car car) throws SQLException {
        String sql = "INSERT INTO cars (is_ready, purpose) VALUES(?, ?) RETURNING id";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, car.isReady());
            statement.setString(2, car.getPurpose());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                String errorMessage = "No id obtained while adding a new question!";
                log.error(errorMessage);
                throw new SQLException(errorMessage);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    public void updateCar(int id, Car updatedCar) throws SQLException {
        String sql = "UPDATE cars SET is_ready=?, purpose=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, updatedCar.isReady());
            statement.setString(2, updatedCar.getPurpose());
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteCar(int id) throws SQLException {
        String sql = "DELETE FROM cars WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }
}
