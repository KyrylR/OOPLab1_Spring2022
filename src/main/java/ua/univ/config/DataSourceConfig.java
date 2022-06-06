package ua.univ.config;

import lombok.extern.slf4j.Slf4j;
import ua.univ.utils.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connectivity
 */
@Slf4j
public class DataSourceConfig {
    private static final String DB_URL = PropertiesUtil.getProperty("db.url");
    private static final String USER = PropertiesUtil.getProperty("db.user");
    private static final String PASS = PropertiesUtil.getProperty("db.password");

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.error("PostgreSQL JDBC Driver is not found. Include it in your library path.");
            return null;
        }

        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
