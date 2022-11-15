package Util;

import Util.FileUtils;

public class ErrorWriter {

    public ErrorWriter(String errorMessage) {
        String message = String.format(
                "{\n" +
                        "   \"type\": \"error\",\n" +
                        "   \"message\": \"%s\"\n" +
                        "}",
                errorMessage
        );
        FileUtils.writeFile(message, "./output.json");
    }

    public ErrorWriter(String errorMessage, String pathFile) {
        String message = String.format(
                "{\n" +
                        "   \"type\": \"error\",\n" +
                        "   \"message\": \"%s\"\n" +
                        "}",
                errorMessage
        );
        FileUtils.writeFile(message, pathFile);
    }
}
