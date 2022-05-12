package ua.univ.service;

import ua.univ.DAO.AutobaseDAO;
import ua.univ.models.Bid;
import ua.univ.models.Driver;
import ua.univ.utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BidService {

    private AutobaseDAO autobaseDAO;

    public BidService() throws SQLException, ClassNotFoundException {
        this.autobaseDAO = new AutobaseDAO();
    }

    public StringBuilder showAll() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<h2>Bids table</h2>\n");

        Bid[] bids = this.autobaseDAO.indexBid().toArray(new Bid[0]);
        stringBuilder.append(Utils.getTable(bids));

        return stringBuilder;
    }

    public StringBuilder showAll(String name){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<h2>Bids table</h2>\n");

        Bid[] bids = this.autobaseDAO.getBidsForDriver(name).toArray(new Bid[0]);

        stringBuilder.append(Utils.getTable(bids));

        return stringBuilder;
    }

    public StringBuilder showSingle(int id) {
        StringBuilder stringBuilder = new StringBuilder();

        Bid bid = this.autobaseDAO.getBid(id);

        stringBuilder.append(Utils.getSingleModelView(bid));

        return stringBuilder;
    }

    public void onAdd(String[] params) {
        String workPurpose = params[0];
        Boolean isFinished = Boolean.parseBoolean(params[1]);
        String driverFeedback = params[2];
        Driver driver = this.autobaseDAO.getDriver(Integer.parseInt(params[3]));
        this.autobaseDAO.saveBid(new Bid(-1, workPurpose, isFinished, driverFeedback, driver));
    }

    public void onDelete(int id) {
        this.autobaseDAO.deleteBid(id);
    }

    public List<Driver> getAllDrivers() {
        return this.autobaseDAO.indexDriver();
    }

    public Bid getBid(int id){
        return this.autobaseDAO.getBid(id);
    }

    public void onUpdate(String[] params){
        int id = Integer.parseInt(params[0]);
        String workPurpose = params[1];
        boolean isFinished = Boolean.parseBoolean(params[2]);
        String driverFeedback = params[3];
        Driver driver = this.autobaseDAO.getDriver(Integer.parseInt(params[4]));

        this.autobaseDAO.updateBid(id, new Bid(-1, workPurpose, isFinished, driverFeedback, driver));
    }
}
