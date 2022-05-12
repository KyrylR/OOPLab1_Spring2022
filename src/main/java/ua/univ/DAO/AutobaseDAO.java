package ua.univ.DAO;

import ua.univ.config.DataSourceConfig;
import ua.univ.models.Bid;
import ua.univ.models.Car;
import ua.univ.models.CarDriver;
import ua.univ.models.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutobaseDAO {
    private final Connection connection;

    public AutobaseDAO() throws ClassNotFoundException, SQLException {
        connection = new DataSourceConfig().getConnection();
    }

    public void stop() throws SQLException {
        connection.close();
    }

    // -------------- Bids block --------------

    public List<Bid> indexBid() {
        List<Bid> bidsList = new ArrayList<>();

        String sql = "SELECT * FROM bids";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String workPurpose = resultSet.getString("work_purpose");
                boolean isFinished = resultSet.getBoolean("is_finished");
                String driverFeedback = resultSet.getString("driver_feedback");
                int driverId = resultSet.getInt("driver_id");
                Driver driver = getDriver(driverId);

                bidsList.add(new Bid(id, workPurpose, isFinished, driverFeedback, driver));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println(" >>     " + e.getMessage());
        }

        return bidsList;
    }

    public Bid getBid(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bids " +
                    "WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return new Bid(resultSet.getInt("id"),
                        resultSet.getString("work_purpose"),
                        resultSet.getBoolean("is_finished"),
                        resultSet.getString("driver_feedback"),
                        getDriver(resultSet.getInt("driver_id")));
            }

            return null;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return null;
        }
    }

    public List<Bid> getBidsForDriver(String name) {
        List<Bid> resultList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM bids " +
                    "WHERE driver_id=(SELECT id FROM drivers " +
                    "WHERE name=?)");
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                resultList.add(new Bid(resultSet.getInt("id"),
                        resultSet.getString("work_purpose"),
                        resultSet.getBoolean("is_finished"),
                        resultSet.getString("driver_feedback"),
                        getDriver(resultSet.getInt("driver_id"))));
            }

            return resultList;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return resultList;
        }
    }

    public boolean saveBid(Bid bid) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bids " +
                    "(work_purpose, is_finished, driver_feedback, driver_id) " +
                    "VALUES(?, ?, ?, (SELECT id from drivers WHERE id=?))");
            statement.setString(1, bid.getWorkPurpose());
            statement.setBoolean(2, bid.isFinished());
            statement.setString(3, bid.getDriverFeedback());
            statement.setInt(4, bid.getDriver().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return false;
        }
    }

    public boolean updateBid(int id, Bid updatedBid) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE bids " +
                            "SET work_purpose=?, is_finished=?, driver_feedback=?, driver_id=(SELECT id from drivers WHERE id=?)" +
                            "WHERE id=?"
            );
            statement.setString(1, updatedBid.getWorkPurpose());
            statement.setBoolean(2, updatedBid.isFinished());
            statement.setString(3, updatedBid.getDriverFeedback());
            statement.setInt(4, updatedBid.getDriver().getId());
            statement.setInt(5, id);
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

    public boolean deleteBid(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM bids " +
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

    // -------------- Drivers block --------------

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

    // -------------- Cars block --------------

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

    // -------------- Car-Driver relations block --------------

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
                        getCar(carId),
                        getDriver(driverId)));
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
                        getCar(resultSet.getInt("car_id")),
                        getDriver(resultSet.getInt("driver_id")));
            }

            return null;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return null;
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
