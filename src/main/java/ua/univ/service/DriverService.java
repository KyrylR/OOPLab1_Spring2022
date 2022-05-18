package ua.univ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import ua.univ.DAO.DriverDAO;
import ua.univ.models.Car;
import ua.univ.models.Driver;

import java.sql.SQLException;
import java.util.List;

public class DriverService {
    private DriverDAO driverDAO;

    public DriverService() throws SQLException, ClassNotFoundException {
        this.driverDAO = new DriverDAO();
    }

    private static String objectToJson(Driver data) {
        try {
            JSONObject joLinks = new JSONObject();
            joLinks.put("self", new JSONObject().put("href", new JSONObject(new Driver(data.getId(), data.getName()))));
            return new JSONObject(data).toString();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    private static String objectsToJson(List<Driver> data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //Set pretty printing of json
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            JSONArray array = new JSONArray();
            for (Driver datum : data) {
                array.put(new JSONObject(datum));
            }
            JSONObject jo = new JSONObject();
            jo.put("drivers", array);
            JSONObject jo2 = new JSONObject();
            jo2.put("_embedded", jo);
            return jo2.toString();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    public String showAll() {
        try {
            return objectsToJson(this.driverDAO.indexDriver());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    public String showSingle(int id) {
        try {
            return objectToJson(this.driverDAO.getDriver(id));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    public String addDriver(Driver driver) {
        this.driverDAO.saveDriver(driver);
        ObjectMapper mapper = new ObjectMapper();
        try {
            driver.setId(this.driverDAO.getMaxGlobalId());
            return mapper.writeValueAsString(driver);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateDriver(int id, Driver driver) {
        this.driverDAO.updateDriver(id, driver);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(driver);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDelete(int id) {
        this.driverDAO.deleteDriver(id);
    }
}
