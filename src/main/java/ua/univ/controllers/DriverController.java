package ua.univ.controllers;

import ua.univ.service.CarDriverService;
import ua.univ.service.DriverService;
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

@WebServlet("/drivers")
public class DriverController extends HttpServlet {
    private DriverService service;

    @Override
    public void init() {
        try {
            this.service = new DriverService();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
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
            int driver_id = Integer.parseInt(request.getParameter("id"));
            stringBuilder.append(service.showSingle(driver_id));

            request.setAttribute("delete_id", driver_id);
        }

        request.setAttribute("objectName", "Driver");

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
                int delete_id = Integer.parseInt(session.getAttribute("delete_id").toString());
                this.service.onDelete(delete_id);
                resp.sendRedirect("/drivers");
                break;
            }
            case "Add": {
                String name = req.getParameter("name");
                String[] params = new String[]{name};
                this.service.onAdd(params);
                resp.sendRedirect("/drivers");
                break;
            }
            default: {
                System.out.println("Not implemented action!");
            }
        }
        session.invalidate();
    }
}
