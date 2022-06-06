package ua.univ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ua.univ.converters.BidConverter;
import ua.univ.dao.BidDAO;
import ua.univ.dto.BidDTO;
import ua.univ.models.Bid;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class BidService implements ItemService {
    private final BidDAO bidDAO;

    private final BidConverter bidConverter;

    public BidService() throws SQLException {
        this.bidDAO = new BidDAO();
        this.bidConverter = new BidConverter();
    }

    private static String objectToJson(Bid data) throws JSONException {
        try {
            return new JSONObject(data).toString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new JSONException(ex.getMessage());
        }
    }

    private static String objectsToJson(List<Bid> data) throws JSONException {
        try {
            JSONArray array = new JSONArray();
            for (Bid datum : data) {
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
            return objectsToJson(this.bidDAO.indexBid());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new SQLException(ex.getMessage());
        }
    }

    public String showSingle(int id) throws SQLException {
        try {
            return objectToJson(this.bidDAO.getBid(id));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new SQLException(ex.getMessage());
        }
    }

    public String addItem(StringBuilder requestBody) throws SQLException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            BidDTO bidDTO = mapper.readValue(requestBody.toString(), BidDTO.class);
            Bid bid = bidConverter.convertToEntity(bidDTO);

            int newId = this.bidDAO.saveBid(bid);

            if (newId == -1) {
                String reason = "Can not save the bid!";
                log.error(reason);
                throw new SQLException(reason);
            }

            bid.setId(newId);
            return mapper.writeValueAsString(bid);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JSONException(e.getMessage());
        }
    }

    public String updateItem(int id, StringBuilder requestBody) throws SQLException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            BidDTO bidDTO = mapper.readValue(requestBody.toString(), BidDTO.class);
            Bid bid = bidConverter.convertToEntity(bidDTO);
            this.bidDAO.updateBid(id, bid);
            return mapper.writeValueAsString(bid);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JSONException(e.getMessage());
        }
    }

    public void onDelete(int id) throws SQLException {
        this.bidDAO.deleteBid(id);
    }
}
