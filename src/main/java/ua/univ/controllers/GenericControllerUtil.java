package ua.univ.controllers;

import lombok.extern.slf4j.Slf4j;
import ua.univ.service.ItemService;
import ua.univ.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@Slf4j
public class GenericControllerUtil {
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";

    private GenericControllerUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static void logReportingError(String queryType) {
        log.error("Error while reporting in " + queryType + "!");
    }

    public static void getItem(ItemService service, HttpServletRequest req, HttpServletResponse resp) {
        try {
            PrintWriter out = resp.getWriter();
            resp.setContentType(CONTENT_TYPE);
            resp.setCharacterEncoding(ENCODING);
            String data;

            int idValue = ServletUtils.getURIId(req.getRequestURI());
            if (idValue == -1) {
                data = service.showAll();
            } else {
                data = service.showSingle(idValue);
            }

            out.print(data);
            out.flush();
        } catch (SQLException exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                resp.getWriter().println(exception.getMessage());
            } catch (IOException e) {
                logReportingError("Get");
            }
            log.error(exception.getMessage());
        } catch (Exception exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.error(exception.getMessage());
        }
    }

    public static void deleteItem(ItemService service, HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = ServletUtils.getURIId(req.getRequestURI());
            service.onDelete(id);
        } catch (SQLException exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                resp.getWriter().println(exception.getMessage());
            } catch (IOException e) {
                logReportingError("Delete");
            }
            log.error(exception.getMessage());
        } catch (Exception exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.error(exception.getMessage());
        }
    }

    public static void postItem(ItemService service, HttpServletRequest req, HttpServletResponse resp) {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder requestBody = new StringBuilder();
            PrintWriter out = resp.getWriter();
            resp.setContentType(CONTENT_TYPE);
            resp.setCharacterEncoding(ENCODING);


            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            String itemJsonString = service.addItem(requestBody);

            out.print(itemJsonString);
            resp.setStatus(200);
        } catch (SQLException exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                resp.getWriter().println(exception.getMessage());
            } catch (IOException e) {
                logReportingError("Post");
            }
            log.error(exception.getMessage());
        } catch (Exception exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.error(exception.getMessage());
        }
    }

    public static void putItem(ItemService service, HttpServletRequest req, HttpServletResponse resp) {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder requestBody = new StringBuilder();
            PrintWriter out = resp.getWriter();
            resp.setContentType(CONTENT_TYPE);
            resp.setCharacterEncoding(ENCODING);

            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            int id = ServletUtils.getURIId(req.getRequestURI());
            String itemJsonString = service.updateItem(id, requestBody);

            out.print(itemJsonString);
            resp.setStatus(200);
        } catch (SQLException exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                resp.getWriter().println(exception.getMessage());
            } catch (IOException e) {
                logReportingError("Put");
            }
            log.error(exception.getMessage());
        } catch (Exception exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.error(exception.getMessage());
        }
    }
}
