package ua.univ.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Driver implements IDefaultModel{
    private int id;
    private String name;

    public Driver(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getURLPattern() {
        return "/drivers";
    }

    @Override
    public Map<String, String> getMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Id", String.valueOf(this.id));
        map.put("Name", this.name);
        return map;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                '}';
    }
}
