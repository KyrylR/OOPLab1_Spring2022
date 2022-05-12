package ua.univ.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Car implements IDefaultModel {
    private int id;
    private boolean isReady;
    private String purpose;

    public Car(int id, boolean isReady, String purpose) {
        this.id = id;
        this.isReady = isReady;
        this.purpose = purpose;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getURLPattern() {
        return "/cars";
    }

    @Override
    public Map<String, String> getMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Id", String.valueOf(this.id));
        map.put("Ready", String.valueOf(this.isReady));
        map.put("Purpose", this.purpose);
        return map;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "Car{" +
                "isReady=" + isReady +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
