package ua.univ.models;

import java.util.Map;

public interface IDefaultModel {
    int getId();

    String urlPattern();

    Map<String, String> createMap();
}
