package peaksoft.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Config {
    private static String url = "jdbc:postgresql://localhost:5432/jdbc_homework_2";
    private static String username = "postgres";
    private static String password = "57206700";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("connected to database");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
