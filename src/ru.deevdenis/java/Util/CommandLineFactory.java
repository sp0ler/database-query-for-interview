package Util;

import Configuration.PostgreSQLJDBC;
import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandLineFactory {

    public static final Map<String, String> argsMap = new HashMap<>();

    public CommandLineFactory(String[] args) {
        parseArgs(args);
        initBDConnection();

    }

    private void parseArgs(@NotNull String [] args) {
        for (String arg : args) {
            String[] parts = arg.split("=");
            argsMap.put(parts[0], parts[1]);
        }
    }

    private void initBDConnection() {
        String ip = argsMap.get("ip");
        String username = argsMap.get("username");
        String password = argsMap.get("password");
        String bdName = argsMap.get("bd_name");

        if (ip == null) ip = System.getenv("ip");
        if (username == null) username = System.getenv("username");
        if (password == null) password = System.getenv("password");
        if (bdName == null) bdName = System.getenv("bd_name");

        PostgreSQLJDBC.initSession(ip, username, password, bdName);
    }
}
