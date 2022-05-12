package ua.univ.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class CarDriver implements IDefaultModel{
    private int id;
    private Car car;
    private Driver driver;

    public CarDriver(int id, Car car, Driver driver) {
        this.id = id;
        this.car = car;
        this.driver = driver;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getURLPattern() {
        return "/carDriver";
    }

    @Override
    public Map<String, String> getMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Id", String.valueOf(this.id));
        map.put("CarFor", this.car.getPurpose());
        map.put("Driver", this.driver.getName());
        return map;
    }

    @Override
    public String toString() {
        return "Car: " +
                this.car +
                ", Driver: " +
                this.driver;
    }
}
