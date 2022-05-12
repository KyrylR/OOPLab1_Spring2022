package ua.univ.controllers;

import ua.univ.service.CarService;
import ua.univ.utils.KeycloakTokenUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cars")
public class CarController extends HttpServlet {
    private CarService service;

    @Override
    public void init() throws ServletException {
        try {
            this.service = new CarService();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("username", KeycloakTokenUtil.getPreferredUsername(request));
        request.setAttribute("roles", KeycloakTokenUtil.getRoles(request));

        StringBuilder stringBuilder = new StringBuilder();

        if (request.getParameter("id") == null) {
            stringBuilder.append(this.service.showAll());
        } else {
            int car_id = Integer.parseInt(request.getParameter("id"));
            stringBuilder.append(service.showSingle(car_id));

            request.setAttribute("delete_id", car_id);
        }

        request.setAttribute("objectName", "Car");

        request.setAttribute("info", stringBuilder);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/index.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String action = req.getParameter("btn_action");
        switch (action) {
            case "Delete": {
                int delete_id = Integer.parseInt(session.getAttribute("delete_id").toString());
                this.service.onDelete(delete_id);
                resp.sendRedirect("/cars");
                break;
            }
            case "Add": {
                String purpose = req.getParameter("purpose");
                String isReady = req.getParameter("ready");
                String[] params = new String[]{isReady, purpose};
                this.service.onAdd(params);
                resp.sendRedirect("/cars");
                break;
            }
            default: {
                System.out.println("Not implemented action!");
            }
        }
        session.invalidate();
    }
}
