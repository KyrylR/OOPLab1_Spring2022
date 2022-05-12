package ua.univ.service;

import ua.univ.DAO.AutobaseDAO;
import ua.univ.models.Car;
import ua.univ.models.CarDriver;
import ua.univ.models.Driver;
import ua.univ.utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDriverService {
    private AutobaseDAO autobaseDAO;

    public CarDriverService() throws SQLException, ClassNotFoundException {
        this.autobaseDAO = new AutobaseDAO();
    }

    public StringBuilder showAll() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<h2>Car-Driver relations table</h2>\n");

        CarDriver[] carDriverRelations = this.autobaseDAO.indexCarDriver().toArray(new CarDriver[0]);
        stringBuilder.append(Utils.getTable(carDriverRelations));

        return stringBuilder;
    }

    public StringBuilder showSingle(int id) {
        StringBuilder stringBuilder = new StringBuilder();

        CarDriver carDriverRelation = this.autobaseDAO.getCarDriver(id);

        stringBuilder.append(Utils.getSingleModelView(carDriverRelation));

        return stringBuilder;
    }

    public void onAdd(String[] params) {
        Car car = this.autobaseDAO.getCar(Integer.parseInt(params[0]));
        Driver driver = this.autobaseDAO.getDriver(Integer.parseInt(params[1]));
        this.autobaseDAO.saveCarDriver(new CarDriver(-1, car, driver));
    }

    public void onDelete(int id) {
        this.autobaseDAO.deleteCarDriver(id);
    }

    public void onUpdate(String[] params) {
        int id = Integer.getInteger(params[0]);
        Car car = this.autobaseDAO.getCar(Integer.parseInt(params[1]));
        Driver driver = this.autobaseDAO.getDriver(Integer.parseInt(params[2]));
        this.autobaseDAO.updateCarDriver(id, new CarDriver(-1, car, driver));
    }

    public List<Car> getAllCars() {
        return this.autobaseDAO.indexCar();
    }

    public List<Driver> getAllDrivers() {
        return this.autobaseDAO.indexDriver();
    }
}
