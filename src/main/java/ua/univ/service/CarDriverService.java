package ua.univ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.univ.converters.CarDriverConverter;
import ua.univ.dao.CarDriverDAO;
import ua.univ.dto.CarDriverDTO;
import ua.univ.models.CarDriver;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class CarDriverService implements ItemService {
    private final CarDriverDAO carDriverDAO;

    private final CarDriverConverter carDriverConverter;

    public CarDriverService() throws SQLException {
        this.carDriverDAO = new CarDriverDAO();
        this.carDriverConverter = new CarDriverConverter();
    }

    private static String objectToJson(CarDriver data) throws JSONException {
        try {
            return new JSONObject(data).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new JSONException(ex.getMessage());
        }
    }

    private static String objectsToJson(List<CarDriver> data) throws JSONException {
        try {
            JSONArray array = new JSONArray();
            for (CarDriver datum : data) {
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
            return objectsToJson(this.carDriverDAO.indexCarDriver());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new SQLException(ex.getMessage());
        }
    }

    public String showSingle(int id) throws SQLException {
        try {
            return objectToJson(this.carDriverDAO.getCarDriver(id));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new SQLException(ex.getMessage());
        }
    }

    public String addItem(StringBuilder requestBody) throws SQLException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CarDriverDTO carDriverDTO = mapper.readValue(requestBody.toString(), CarDriverDTO.class);
            CarDriver carDriver = carDriverConverter.convertToEntity(carDriverDTO);
            int newId = this.carDriverDAO.saveCarDriver(carDriver);

            if (newId == -1) {
                String reason = "Can not save the car-driver!";
                log.error(reason);
                throw new SQLException(reason);
            }

            carDriver.setId(newId);
            return mapper.writeValueAsString(carDriver);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JSONException(e);
        }
    }

    public String updateItem(int id, StringBuilder requestBody) throws SQLException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CarDriverDTO carDriverDTO = mapper.readValue(requestBody.toString(), CarDriverDTO.class);
            CarDriver carDriver = carDriverConverter.convertToEntity(carDriverDTO);
            this.carDriverDAO.updateCarDriver(id, carDriver);

            return mapper.writeValueAsString(carDriver);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JSONException(e);
        }
    }

    public void onDelete(int id) throws SQLException {
        this.carDriverDAO.deleteCarDriver(id);
    }
}
