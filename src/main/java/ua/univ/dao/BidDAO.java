package ua.univ.dao;

import lombok.extern.slf4j.Slf4j;
import ua.univ.config.DataSourceConfig;
import ua.univ.models.Bid;
import ua.univ.models.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BidDAO {
    private static final String WORK_PURPOSE_COLUMN = "work_purpose";
    private static final String IS_FINISHED_COLUMN = "is_finished";
    private static final String DRIVER_FEEDBACK_COLUMN = "driver_feedback";
    private static final String DRIVER_ID_COLUMN = "driver_id";
    private final Connection connection;
    private final DriverDAO driverDAO;

    public BidDAO() throws SQLException {
        try {
            connection = new DataSourceConfig().getConnection();
        } catch (SQLException e) {
            String errorMessage = "Connection error.";
            log.error(errorMessage);
            throw new SQLException(errorMessage);
        }
        driverDAO = new DriverDAO();
    }

    public List<Bid> indexBid() throws SQLException {
        List<Bid> bidsList = new ArrayList<>();
        String sql = "SELECT * FROM bids";

        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String workPurpose = resultSet.getString(WORK_PURPOSE_COLUMN);
                boolean isFinished = resultSet.getBoolean(IS_FINISHED_COLUMN);
                String driverFeedback = resultSet.getString(DRIVER_FEEDBACK_COLUMN);
                int driverId = resultSet.getInt(DRIVER_ID_COLUMN);
                Driver driver = driverDAO.getDriver(driverId);

                bidsList.add(new Bid(id, workPurpose, isFinished, driverFeedback, driver));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }

        return bidsList;
    }

    public Bid getBid(int id) throws SQLException {
        String sql = "SELECT * FROM bids WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Bid(resultSet.getInt("id"),
                        resultSet.getString(WORK_PURPOSE_COLUMN),
                        resultSet.getBoolean(IS_FINISHED_COLUMN),
                        resultSet.getString(DRIVER_FEEDBACK_COLUMN),
                        driverDAO.getDriver(resultSet.getInt(DRIVER_ID_COLUMN)));
            }

            return null;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    private void validate(Bid bid) throws SQLException {
        String sql = "SELECT id from drivers WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bid.getDriverId());

            ResultSet resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                String errorMessage = String.format("Could not find a Driver with id: %d", bid.getDriverId());
                log.error(errorMessage);
                throw new SQLException(errorMessage);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }


    public int saveBid(Bid bid) throws SQLException {
        String sql = "INSERT INTO bids (work_purpose, is_finished, driver_feedback, driver_id) VALUES(?, ?, ?, ?) RETURNING id";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            validate(bid);

            ps.setString(1, bid.getWorkPurpose());
            ps.setBoolean(2, bid.isFinished());
            ps.setString(3, bid.getDriverFeedback());
            ps.setInt(4, bid.getDriverId());
            ResultSet rs = ps.executeQuery();
            connection.commit();

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

    public void updateBid(int id, Bid updatedBid) throws SQLException {
        String sql2 = "UPDATE bids SET work_purpose=?, is_finished=?, driver_feedback=?, driver_id=? WHERE id=?";
        try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
            connection.setAutoCommit(false);
            validate(updatedBid);

            ps2.setString(1, updatedBid.getWorkPurpose());
            ps2.setBoolean(2, updatedBid.isFinished());
            ps2.setString(3, updatedBid.getDriverFeedback());
            ps2.setInt(4, updatedBid.getDriverId());
            ps2.setInt(5, id);
            ps2.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteBid(int id) throws SQLException {
        String sql = "DELETE FROM bids WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }
}
