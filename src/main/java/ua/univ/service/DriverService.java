package ua.univ.service;

import ua.univ.DAO.AutobaseDAO;
import ua.univ.models.Driver;
import ua.univ.utils.Utils;

import java.sql.SQLException;
import java.util.List;

public class DriverService {
    private AutobaseDAO autobaseDAO;

    public DriverService() throws SQLException, ClassNotFoundException {
        this.autobaseDAO = new AutobaseDAO();
    }

    public StringBuilder showAll() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<h2>Drivers table</h2>\n");

        Driver[] drivers = this.autobaseDAO.indexDriver().toArray(new Driver[0]);
        stringBuilder.append(Utils.getTable(drivers));

        return stringBuilder;
    }

    public StringBuilder showSingle(int id) {
        StringBuilder stringBuilder = new StringBuilder();

        Driver driver = this.autobaseDAO.getDriver(id);

        stringBuilder.append(Utils.getSingleModelView(driver));

        return stringBuilder;
    }

    public void onAdd(String[] params) {
        String name = params[0];
        this.autobaseDAO.saveDriver(new Driver(-1, name));
    }

    public void onDelete(int id) {
        this.autobaseDAO.deleteDriver(id);
    }
}
