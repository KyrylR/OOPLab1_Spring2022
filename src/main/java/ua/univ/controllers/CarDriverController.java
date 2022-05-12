package ua.univ.controllers;

import org.keycloak.representations.AccessToken;
import ua.univ.service.BidService;
import ua.univ.service.CarDriverService;
import ua.univ.utils.KeycloakTokenUtil;
import ua.univ.utils.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@WebServlet("/carDriver")
public class CarDriverController extends HttpServlet {
    private CarDriverService service;

    @Override
    public void init() {
        try {
            this.service = new CarDriverService();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("username", KeycloakTokenUtil.getPreferredUsername(request));
        request.setAttribute("roles",  KeycloakTokenUtil.getRoles(request));

        StringBuilder stringBuilder = new StringBuilder();

        if (request.getParameter("id") == null) {
            request.setAttribute("delete_id", null);
            stringBuilder.append(service.showAll());
        } else {
            int carDriverId = Integer.parseInt(request.getParameter("id"));
            stringBuilder.append(service.showSingle(carDriverId));

            request.setAttribute("delete_id", carDriverId);
        }

        request.setAttribute("carList", this.service.getAllCars());
        request.setAttribute("driverList", this.service.getAllDrivers());


        request.setAttribute("objectName", "CarDriver");

        request.setAttribute("info", stringBuilder);
        request.setAttribute("action", "None");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/index.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String action = req.getParameter("btn_action");
        switch (action) {
            case "Delete": {
                int deleteId = Integer.parseInt(session.getAttribute("delete_id").toString());
                this.service.onDelete(deleteId);
                resp.sendRedirect("/carDriver");
                break;
            }
            case "Add": {
                String carId = req.getParameter("car_id");
                String driverId = req.getParameter("driver_id");
                String[] params = new String[] {carId, driverId};
                this.service.onAdd(params);
                resp.sendRedirect("/carDriver");
                break;
            }
            default: {
                System.out.println("Not implemented action!");
            }
        }
        session.invalidate();
    }
}
