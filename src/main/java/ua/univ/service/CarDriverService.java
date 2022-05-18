package ua.univ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import ua.univ.DAO.CarDriverDAO;
import ua.univ.models.CarDriver;

import java.sql.SQLException;
import java.util.List;

public class CarDriverService {
    private CarDriverDAO carDriverDAO;

    public CarDriverService() throws SQLException, ClassNotFoundException {
        this.carDriverDAO = new CarDriverDAO();
    }

    private static String objectToJson(CarDriver data) {
        try {
            return new JSONObject(data).toString();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    private static String objectsToJson(List<CarDriver> data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //Set pretty printing of json
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            JSONArray array = new JSONArray();
            for (CarDriver datum : data) {
                array.put(new JSONObject(datum));
            }
            JSONObject jo = new JSONObject();
            jo.put("carDrivers", array);
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
            return objectsToJson(this.carDriverDAO.indexCarDriver());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    public String showSingle(int id) {
        try {
            return objectToJson(this.carDriverDAO.getCarDriver(id));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    public String addCarDriver(CarDriver carDriver) {
        this.carDriverDAO.saveCarDriver(carDriver);
        ObjectMapper mapper = new ObjectMapper();
        try {
            carDriver.setId(this.carDriverDAO.getMaxGlobalId());
            return mapper.writeValueAsString(carDriver);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateCarDriver(int id, CarDriver carDriver) {
        this.carDriverDAO.updateCarDriver(id, carDriver);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(carDriver);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDelete(int id) {
        this.carDriverDAO.deleteCarDriver(id);
    }
}
