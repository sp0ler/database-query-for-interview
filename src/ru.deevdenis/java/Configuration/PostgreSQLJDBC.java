package Configuration;

import Util.ErrorWriter;
import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLJDBC {

    private static Connection instance;
    private static String IP;
    private static String BD_NAME;
    private static String USER;
    private static String PASSWORD;

    public static void initSession(@NotNull String ip,
                            @NotNull String username,
                            @NotNull String password,
                            @NotNull String bdName) {
        IP = ip;
        USER = username;
        PASSWORD = password;
        BD_NAME = bdName;
    }

    public static Connection sessionFactory() {
        if (instance == null) {
            String url = String.format("jdbc:postgresql://%s/%s", IP, BD_NAME);

            Properties props = new Properties();
            props.setProperty("user", USER);
            props.setProperty("password", PASSWORD);
            try {
                instance = DriverManager.getConnection(url, props);

            } catch (SQLException e) {
                throw new Error("Невозможно подключить к базе данных.");
            }
        }

        return instance;
    }

    public static void close() {
        try {
            instance.close();
        } catch (SQLException e) {
            throw new Error("Невозможно закрыть сессию к базе данных.");
        }
    }
}
