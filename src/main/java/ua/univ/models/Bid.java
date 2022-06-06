package ua.univ.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
public class Bid implements IDefaultModel {
    private int id;
    private String workPurpose;
    private boolean isFinished;
    private String driverFeedback;

    private Driver driver;

    public int getDriverId() {
        return driver.getId();
    }


    public Bid(int id, String workPurpose, boolean isFinished, String driverFeedback, Driver driver) {
        this.id = id;
        this.workPurpose = workPurpose;
        this.isFinished = isFinished;
        this.driverFeedback = driverFeedback;
        this.driver = driver;
    }

    @Override
    public int getId() {
        return this.id;
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
