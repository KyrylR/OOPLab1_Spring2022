package ua.univ.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Driver implements IDefaultModel {
    private int id;
    private String name;

    public Driver(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String urlPattern() {
        return "/drivers";
    }

    @Override
    public Map<String, String> createMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Id", String.valueOf(this.id));
        map.put("Name", this.name);
        return map;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                '}';
    }
}
