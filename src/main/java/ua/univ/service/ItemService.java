package ua.univ.service;

import org.json.JSONException;

import java.sql.SQLException;

public interface ItemService {
    String showAll() throws SQLException, JSONException;

    String showSingle(int id) throws SQLException, JSONException;

    String addItem(StringBuilder requestBody) throws SQLException;

    String updateItem(int id, StringBuilder requestBody) throws SQLException;

    void onDelete(int id) throws SQLException;
}
