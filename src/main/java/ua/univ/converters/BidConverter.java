package ua.univ.converters;

import lombok.AllArgsConstructor;
import ua.univ.dao.DriverDAO;
import ua.univ.dto.BidDTO;
import ua.univ.models.Bid;
import ua.univ.models.Driver;

import java.sql.SQLException;


@AllArgsConstructor
public class BidConverter {
    private final DriverDAO driverDAO;

    public BidConverter() throws SQLException {
        this.driverDAO = new DriverDAO();
    }

    public Bid convertToEntity(BidDTO bidDTO) throws SQLException {
        Driver driver = null;
        try {
            driver = driverDAO.getDriver(Integer.parseInt(bidDTO.getDriverId()));
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return new Bid(-1, bidDTO.getWorkPurpose(), bidDTO.isFinished(), bidDTO.getDriverFeedback(), driver);
    }
}
