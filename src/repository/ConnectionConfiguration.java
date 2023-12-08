package repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConfiguration {
    private static ConnectionConfiguration instance;
    private Connection connection;

    private ConnectionConfiguration() {
        try {
            String url = System.getenv("url");
            String username = System.getenv("username");
            String password = System.getenv("password");

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(url, username, password);

            System.out.println("Connected successfully to database");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static ConnectionConfiguration getInstance() {
        if (instance == null) {
            synchronized (ConnectionConfiguration.class) {
                if (instance == null) {
                    instance = new ConnectionConfiguration();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
