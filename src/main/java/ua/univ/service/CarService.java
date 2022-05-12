package ua.univ.service;

import ua.univ.DAO.AutobaseDAO;
import ua.univ.models.Car;
import ua.univ.utils.Utils;

import java.sql.SQLException;
import java.util.List;

public class CarService {

    private AutobaseDAO autobaseDAO;

    public CarService() throws SQLException, ClassNotFoundException {
        this.autobaseDAO = new AutobaseDAO();
    }

    public StringBuilder showAll() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<h2>Cars table</h2>\n");

        Car[] cars = this.autobaseDAO.indexCar().toArray(new Car[0]);
        stringBuilder.append(Utils.getTable(cars));

        return stringBuilder;
    }

    public StringBuilder showSingle(int id) {
        StringBuilder stringBuilder = new StringBuilder();

        Car car = this.autobaseDAO.getCar(id);

        stringBuilder.append(Utils.getSingleModelView(car));

        return stringBuilder;
    }

    public void onAdd(String[] params) {
        Boolean isReady = Boolean.parseBoolean(params[0]);
        String purpose = params[1];
        this.autobaseDAO.saveCar(new Car(-1, isReady, purpose));
    }

    public void onDelete(int id) {
        this.autobaseDAO.deleteCar(id);
    }

    public void onUpdate(String[] params) {
        int id = Integer.parseInt(params[0]);
        Boolean isReady = Boolean.parseBoolean(params[1]);
        String purpose = params[1];
        this.autobaseDAO.updateCar(id, new Car(-1, isReady, purpose));
    }
}
