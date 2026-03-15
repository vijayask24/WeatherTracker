package utils;

import java.nio.file.*;
import java.util.*;

public class FileReaderUtil {

    // =============================
    // Read US states from metadata
    // =============================
    public static List<String> readStates() {
        return readFile("src/test/resources/metadata/us_states.txt");
    }

    // =============================
    // Read international cities from metadata
    // =============================
    public static List<String> readInternationalCities() {
        return readFile("src/test/resources/metadata/Internation._cities.txt");
    }

    // =============================
    // Read both US states + international cities
    // =============================
    public static List<String> readAllLocations() {
        List<String> allLocations = new ArrayList<>();
        allLocations.addAll(readStates());
        allLocations.addAll(readInternationalCities());
        return allLocations;
    }

    // =============================
    // Generic helper to read any file
    // =============================
    private static List<String> readFile(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
