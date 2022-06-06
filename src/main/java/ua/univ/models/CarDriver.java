package ua.univ.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CarDriver implements IDefaultModel {
    private int id;
    private Car car;
    private Driver driver;

    public CarDriver(int id, Car car, Driver driver) {
        this.id = id;
        this.car = car;
        this.driver = driver;
    }

    public int getDriverId() {
        return driver.getId();
    }

    public int getCarId() {
        return car.getId();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String urlPattern() {
        return "/carDriver";
    }

    @Override
    public Map<String, String> createMap() {
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
