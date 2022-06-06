package ua.univ.controllers;

import lombok.extern.slf4j.Slf4j;
import ua.univ.service.CarDriverService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Slf4j
@WebServlet("/api/carDrivers/*")
public class CarDriverController extends HttpServlet {
    private static CarDriverService service;

    static {
        try {
            service = new CarDriverService();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        GenericControllerUtil.getItem(service, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        GenericControllerUtil.postItem(service, req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        GenericControllerUtil.deleteItem(service, req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        GenericControllerUtil.putItem(service, req, resp);
    }
}
