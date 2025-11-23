package app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // NOTE: update USER and PASS to match your MySQL credentials.
    // For MySQL 8+ the driver class is `com.mysql.cj.jdbc.Driver` and
    // adding `serverTimezone` and disabling SSL (if not configured) avoids common
    // errors.
    private static final String URL = "jdbc:mysql://localhost:3306/amazonapp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "@jivi001"; // set your root password here (or use a dedicated DB user)

    public static Connection getConnection() {
        try {
            // Ensure the MySQL driver is loaded (helps on some classpath setups)
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException cnfe) {
                // Driver not found on classpath — will still surface via DriverManager, but log
                // clearly
                System.err.println("MySQL JDBC driver not found. Make sure the connector JAR is on the classpath.");
            }

            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            // Print useful diagnostics to help debug connection issues
            System.err.println("Failed to obtain DB connection to: " + URL);

            // Check for Access Denied (Error 1045)
            if (e.getErrorCode() == 1045) {
                System.err.println("********************************************************************************");
                System.err.println("ERROR: ACCESS DENIED FOR USER '" + USER + "'");
                System.err.println("Please open 'src/main/java/app/db/Database.java' and set the correct DB password.");
                System.err.println("********************************************************************************");
            }

            e.printStackTrace();
            throw new RuntimeException("Unable to connect to database", e);
        }
    }
}
