package ua.univ.DAO;

import ua.univ.config.DataSourceConfig;
import ua.univ.models.Bid;
import ua.univ.models.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BidDAO {
    private final Connection connection;

    private DriverDAO driverDAO;
    public BidDAO() throws ClassNotFoundException, SQLException {
        connection = new DataSourceConfig().getConnection();
        driverDAO = new DriverDAO();
    }

    public void stop() throws SQLException {
        connection.close();
    }


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
                Driver driver = driverDAO.getDriver(driverId);

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
                        driverDAO.getDriver(resultSet.getInt("driver_id")));
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
                        driverDAO.getDriver(resultSet.getInt("driver_id"))));
            }

            return resultList;
        } catch (SQLException e) {
            System.out.println(" >>     " + e.getMessage());
            return resultList;
        }
    }

    public int getMaxGlobalId() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT max(id) FROM bids");

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
}
