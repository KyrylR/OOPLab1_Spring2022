package ua.univ.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
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

    public void setReady(boolean ready) {
        this.isReady = ready;
    }

    @Override
    public String urlPattern() {
        return "/cars";
    }

    @Override
    public Map<String, String> createMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Id", String.valueOf(this.id));
        map.put("Ready", String.valueOf(this.isReady));
        map.put("Purpose", this.purpose);
        return map;
    }

    @Override
    public String toString() {
        return "Car{" +
                "isReady=" + isReady +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
