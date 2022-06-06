package ua.univ.converters;

import lombok.AllArgsConstructor;
import ua.univ.dao.CarDAO;
import ua.univ.dao.DriverDAO;
import ua.univ.dto.CarDriverDTO;
import ua.univ.models.Car;
import ua.univ.models.CarDriver;
import ua.univ.models.Driver;

import java.sql.SQLException;


@AllArgsConstructor
public class CarDriverConverter {
    private final DriverDAO driverDAO;
    private final CarDAO carDAO;

    public CarDriverConverter() throws SQLException {
        this.driverDAO = new DriverDAO();
        this.carDAO = new CarDAO();
    }


    public CarDriver convertToEntity(CarDriverDTO carDriverDTO) throws SQLException {
        Car car = null;
        Driver driver = null;
        try {
            car = carDAO.getCar(Integer.parseInt(carDriverDTO.getCarId()));
            driver = driverDAO.getDriver(Integer.parseInt(carDriverDTO.getDriverId()));
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return new CarDriver(-1, car, driver);
    }
}
