package ua.univ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import ua.univ.DAO.BidDAO;
import ua.univ.models.Bid;

import java.sql.SQLException;
import java.util.List;

public class BidService {

    private BidDAO bidDAO;

    public BidService() throws SQLException, ClassNotFoundException {
        this.bidDAO = new BidDAO();
    }

    private static String objectToJson(Bid data) {
        try {
            JSONObject joLinks = new JSONObject();
            joLinks.put("self", new JSONObject().put("href", new JSONObject(data.getDriver())));
            return new JSONObject(data).toString();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    private static String objectsToJson(List<Bid> data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //Set pretty printing of json
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            JSONArray array = new JSONArray();
            for (Bid datum : data) {
                array.put(new JSONObject(datum));
            }
            JSONObject jo = new JSONObject();
            jo.put("bids", array);
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
            return objectsToJson(this.bidDAO.indexBid());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    public String showSingle(int id) {
        try {
            return objectToJson(this.bidDAO.getBid(id));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }

    public String addBid(Bid bid) {
        this.bidDAO.saveBid(bid);
        ObjectMapper mapper = new ObjectMapper();
        try {
            bid.setId(this.bidDAO.getMaxGlobalId());
            return mapper.writeValueAsString(bid);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateBid(int id, Bid bid) {
        this.bidDAO.updateBid(id, bid);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(bid);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDelete(int id) {
        this.bidDAO.deleteBid(id);
    }
}
