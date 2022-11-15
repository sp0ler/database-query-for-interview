package Util;

import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {


    public static List<String> readFile(String pathFile) throws IOException {
        List<String> stringList = new ArrayList<>();

        try {
            Path path = Paths.get(pathFile);
            stringList = Files.readAllLines(path).stream()
                    .map(it -> it.replaceAll("[\\[\\]\\{\\}\"]", ""))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new Error(String.format("Файл '%s' не найден!", pathFile));
        }

        return stringList;
    }

    public static void writeFile(@NotNull String str,@NotNull String pathFile) {
        Path path;
        try {
            path = Paths.get(pathFile);
        } catch (NullPointerException e) {
            throw new Error("Не указан путь output.json");
        }

        byte[] strToBytes = str.getBytes();

        try {
            Files.write(path, strToBytes);
        } catch (IOException e) {
            throw new Error("Невозможно записать файл с таким путем.");
        }
    }
}
