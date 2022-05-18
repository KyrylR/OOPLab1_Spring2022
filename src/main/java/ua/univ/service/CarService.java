package ua.univ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import ua.univ.DAO.CarDAO;
import ua.univ.models.Car;
import ua.univ.models.Driver;
import ua.univ.utils.Utils;

import java.sql.SQLException;
import java.util.List;

public class CarService {

    private CarDAO carDAO;

    public CarService() throws SQLException, ClassNotFoundException {
        this.carDAO = new CarDAO();
    }

    private static String objectToJson(Car data) {
        try {
            JSONObject joLinks = new JSONObject();
            joLinks.put("self", new JSONObject().put("href", new JSONObject(new Car(data.getId(), data.isReady(), data.getPurpose()))));
            return new JSONObject(data).toString();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    private static String objectsToJson(List<Car> data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //Set pretty printing of json
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            JSONArray array = new JSONArray();
            for (Car datum : data) {
                array.put(new JSONObject(datum));
            }
            JSONObject jo = new JSONObject();
            jo.put("cars", array);
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
            return objectsToJson(this.carDAO.indexCar());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    public String showSingle(int id) {
        try {
            return objectToJson(this.carDAO.getCar(id));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    public String addCar(Car car) {
        this.carDAO.saveCar(car);
        ObjectMapper mapper = new ObjectMapper();
        try {
            car.setId(this.carDAO.getMaxGlobalId());
            return mapper.writeValueAsString(car);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateCar(int id, Car car) {
        this.carDAO.updateCar(id, car);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(car);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDelete(int id) {
        this.carDAO.deleteCar(id);
    }
}
