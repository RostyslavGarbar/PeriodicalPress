package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionManager {
    private static DBConnectionManager dbConnectionManager;

    private DBConnectionManager() { }

    public static synchronized DBConnectionManager getInstance() {
        if (dbConnectionManager == null) {
            dbConnectionManager = new DBConnectionManager();
        }
        return dbConnectionManager;
    }

    public Connection getDBConnection() throws SQLException {
        try (FileInputStream fileInputStream = new FileInputStream("app.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String connectionUrl = properties.getProperty("connection.url");
            return DriverManager.getConnection(connectionUrl);
        } catch (IOException e) {
            return null;
        }
    }
}
