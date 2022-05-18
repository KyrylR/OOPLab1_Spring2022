package ua.univ.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Bid implements IDefaultModel {
    private int id;
    private String workPurpose;
    private boolean isFinished;
    private String driverFeedback;

    private Driver driver;

    public Bid() {
    }

    public Bid(int id, String workPurpose, boolean isFinished, String driverFeedback, Driver driver) {
        this.id = id;
        this.workPurpose = workPurpose;
        this.isFinished = isFinished;
        this.driverFeedback = driverFeedback;
        this.driver = driver;
    }

    public String getWorkPurpose() {
        return workPurpose;
    }

    public void setWorkPurpose(String workPurpose) {
        this.workPurpose = workPurpose;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void setIsFinished(boolean finished) {
        isFinished = finished;
    }

    public String getDriverFeedback() {
        return driverFeedback;
    }

    public void setDriverFeedback(String driverFeedback) {
        this.driverFeedback = driverFeedback;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getDriverId() {
        return driver.getId();
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String urlPattern() {
        return "/bids";
    }

    @Override
    public Map<String, String> createMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Id", String.valueOf(this.id));
        map.put("WorkPurpose", this.workPurpose);
        map.put("Finished", (this.isFinished ? "True" : "False"));
        map.put("DriverFeedback", this.driverFeedback);
        map.put("Driver", this.driver.getName());
        return map;
    }

    @Override
    public String toString() {
        return "Driver: " +
                this.driver +
                ", Work purpose: " +
                this.workPurpose +
                ", Finished: " +
                this.isFinished +
                ", Driver feedback: " +
                this.driverFeedback;
    }
}
