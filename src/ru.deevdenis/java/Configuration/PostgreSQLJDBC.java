package Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PostgreSQLJDBC {

    private static Connection instance;
    private static final String URL = System.getenv("");
    private static final String BD_NAME = System.getenv("");
    private static final String USER = System.getenv("");
    private static final String PASSWORD = System.getenv("");

    public static Connection sessionFactory() {
        if (instance == null) {
            String url = String.format("jdbc:postgresql://%s/%s", URL, BD_NAME);
            Properties props = new Properties();
            props.setProperty("user", USER);
            props.setProperty("password", PASSWORD);
            try {
                Connection conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static void close() {
        try {
            instance.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
