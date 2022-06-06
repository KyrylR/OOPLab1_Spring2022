package ua.univ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.univ.dao.DriverDAO;
import ua.univ.models.Driver;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class DriverService implements ItemService {
    private final DriverDAO driverDAO;

    public DriverService() throws SQLException {
        this.driverDAO = new DriverDAO();
    }

    private static String objectToJson(Driver data) {
        try {
            return new JSONObject(data).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new JSONException(ex.getMessage());
        }
    }

    private static String objectsToJson(List<Driver> data) {
        try {
            JSONArray array = new JSONArray();
            for (Driver datum : data) {
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
            return objectsToJson(this.driverDAO.indexDriver());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new SQLException(ex.getMessage());
        }
    }

    public String showSingle(int id) throws SQLException {
        try {
            return objectToJson(this.driverDAO.getDriver(id));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new SQLException(ex.getMessage());
        }
    }

    public String addItem(StringBuilder requestBody) throws SQLException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Driver driver = mapper.readValue(requestBody.toString(), Driver.class);
            int newId = this.driverDAO.saveDriver(driver);

            if (newId == -1) {
                log.error("Can not save the bid!");
                throw new SQLException("Can not save the bid!");
            }

            driver.setId(newId);
            return mapper.writeValueAsString(driver);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JSONException(e.getMessage());
        }
    }

    public String updateItem(int id, StringBuilder requestBody) throws SQLException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Driver driver = mapper.readValue(requestBody.toString(), Driver.class);
            this.driverDAO.updateDriver(id, driver);

            return mapper.writeValueAsString(driver);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JSONException(e.getMessage());
        }
    }

    public void onDelete(int id) throws SQLException {
        this.driverDAO.deleteDriver(id);
    }
}
