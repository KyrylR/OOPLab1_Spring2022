package ua.univ.controllers;

import org.keycloak.representations.AccessToken;
import ua.univ.models.Bid;
import ua.univ.service.BidService;
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

@WebServlet("/bids")
public class BidController extends HttpServlet {
    private BidService service;

    @Override
    public void init() {
        try {
            this.service = new BidService();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("username", KeycloakTokenUtil.getPreferredUsername(request));
        Set<String> roles = KeycloakTokenUtil.getRoles(request);
        AccessToken accessToken = KeycloakTokenUtil.getToken(request);
        request.setAttribute("roles", KeycloakTokenUtil.getRoles(request));

        StringBuilder stringBuilder = new StringBuilder();

        if (request.getParameter("id") == null) {
            if (roles.contains("admin") || roles.contains("manager")) {
                stringBuilder.append(service.showAll());
            } else if (roles.contains("driver")) {
                stringBuilder.append(service.showAll(accessToken.getName()));
            }
        } else {
            int bid_id = Integer.parseInt(request.getParameter("id"));
            stringBuilder.append(service.showSingle(bid_id));

            Bid bid = service.getBid(bid_id);

            request.setAttribute("finished", String.valueOf(bid.isFinished()));
            request.setAttribute("feedback", String.valueOf(bid.getDriverFeedback()));
            request.setAttribute("delete_id", bid_id);
        }

        request.setAttribute("driverList", this.service.getAllDrivers());
        request.setAttribute("objectName", "Bid");

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
                resp.sendRedirect("/bids");
                break;
            }
            case "Add": {
                String workPurpose = req.getParameter("name");
                String isFinished = req.getParameter("finished");
                String driverFeedback = req.getParameter("driverFeedback");
                String driverId = req.getParameter("driver_id");
                String[] params = new String[]{workPurpose, isFinished, driverFeedback, driverId};
                this.service.onAdd(params);
                resp.sendRedirect("/bids");
                break;
            }
            case "Update": {
                int update_id = Integer.parseInt(session.getAttribute("update_id").toString());
                Bid bid = service.getBid(update_id);
                String finished = (null == req.getParameter("finished") || req.getParameter("finished").equals("")) ? "false" : req.getParameter("finished");
                String feedback = (null == req.getParameter("feedback")) ? "" : req.getParameter("feedback");
                String[] params = new String[]{String.valueOf(bid.getId()),
                        bid.getWorkPurpose(),
                        finished,
                        feedback, String.valueOf(bid.getDriver().getId())};
                this.service.onUpdate(params);
                resp.sendRedirect("/bids");
                break;
            }
            default: {
                System.out.println("Not implemented action!");
            }
        }
        session.invalidate();
    }
}
