package ua.univ.models;

import java.util.Map;

public interface IDefaultModel {
    int getId();

    String getURLPattern();

    Map<String, String> getMap();
}
