package ua.univ.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.univ.models.Driver;
import ua.univ.service.DriverService;
import ua.univ.utils.ServletUtils;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/api/drivers/*")
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
//    @RolesAllowed("ROLE_ADMIN")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String data = "";

            int idValue = ServletUtils.getURIId(request.getRequestURI());
            if (idValue == -1) {
                data = this.service.showAll();
            } else {
                data = service.showSingle(idValue);
            }

            out.print(data);
            out.flush();
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println(exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder requestBody = new StringBuilder();
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Driver driver = new ObjectMapper().readValue(requestBody.toString(), Driver.class);
            String driverJsonString = this.service.addDriver(driver);

            out.print(driverJsonString);
            resp.setStatus(200);
        } catch (Exception exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println(exception);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id = ServletUtils.getURIId(req.getRequestURI());
            this.service.onDelete(id);
        } catch (Exception exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println(exception);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("PATCH")) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder requestBody = new StringBuilder();
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            int id = ServletUtils.getURIId(req.getRequestURI());
            Driver driver = new ObjectMapper().readValue(requestBody.toString(), Driver.class);
            String driverJsonString = this.service.updateDriver(id, driver);

            out.print(driverJsonString);
            resp.setStatus(200);
        } catch (Exception exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println(exception);
        }
    }
}
