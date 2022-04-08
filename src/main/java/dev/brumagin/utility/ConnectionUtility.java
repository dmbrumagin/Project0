package dev.brumagin.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    public static Connection createConnection() {
        Connection connection;
        try {
            String url = System.getenv("BANKINGDB");
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            throw new Error("", e);
        }

        return  connection;
    }
}
