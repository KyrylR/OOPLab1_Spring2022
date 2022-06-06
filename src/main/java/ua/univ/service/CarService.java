package ua.univ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.univ.dao.CarDAO;
import ua.univ.models.Car;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class CarService implements ItemService {

    private final CarDAO carDAO;

    public CarService() throws SQLException {
        this.carDAO = new CarDAO();
    }

    private static String objectToJson(Car data) throws JSONException {
        try {
            return new JSONObject(data).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new JSONException(ex.getMessage());
        }
    }

    private static String objectsToJson(List<Car> data) throws JSONException {
        try {
            JSONArray array = new JSONArray();
            for (Car datum : data) {
                array.put(new JSONObject(datum));
            }
            return array.toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new JSONException(ex.getMessage());
        }
    }

    public String showAll() throws SQLException {
        try {
            return objectsToJson(this.carDAO.indexCar());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new SQLException(ex.getMessage());
        }
    }

    public String showSingle(int id) throws SQLException {
        try {
            return objectToJson(this.carDAO.getCar(id));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new SQLException(ex.getMessage());
        }
    }

    public String addItem(StringBuilder requestBody) throws SQLException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Car car = mapper.readValue(requestBody.toString(), Car.class);
            int newId = this.carDAO.saveCar(car);

            car.setId(newId);
            return mapper.writeValueAsString(car);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JSONException(e.getMessage());
        }
    }

    public String updateItem(int id, StringBuilder requestBody) throws SQLException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Car car = mapper.readValue(requestBody.toString(), Car.class);
            this.carDAO.updateCar(id, car);
            return mapper.writeValueAsString(car);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JSONException(e.getMessage());
        }
    }

    public void onDelete(int id) throws SQLException {
        this.carDAO.deleteCar(id);
    }
}
