package ua.univ.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceConfig {
    /*  Database credentials */
    static final String DB_URL = "jdbc:postgresql://localhost:5432/Autobase";
    static final String USER = "postgres";
    static final String PASS = "password";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return null;
        }

        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
