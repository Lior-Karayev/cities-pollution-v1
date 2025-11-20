package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static final Properties props = new Properties();

    static {
        try(FileInputStream input = new FileInputStream("config.properties")){
            props.load(input);
        } catch (IOException e) {
            System.out.println("ERROR: config.properties file not found.");
            e.printStackTrace();
        }
    }

//    private static final String DB_URL = "jdbc:mysql://localhost:3306/cities_pollution_db";
//    private static final String USER = "root";
//    private static final String PASS = "L0r1@20803";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
        );
    }
}
