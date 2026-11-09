package CPS2231_FinalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class Database extends connection{
    // CPS2231_FinalProject.Database URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USER = "root"; // Default WAMP server username
    private static final String PASSWORD = ""; // Default WAMP server password (usually blank)

    // Load the JDBC driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get a CPS2231_FinalProject.connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

