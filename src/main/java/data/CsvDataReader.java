package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader {
    private CsvDataReader(){}

    public static List<String> getCsvFileLines(String path){
        Path filePath = Paths.get(path);

        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<String> getMaxPollutionCities(String path){
        // Get the countries with minimal air quality or maximum water pollution
        return new ArrayList<>();
    }

    public static List<String> getMinPollutionCities(String path){
        // Get the countries with maximum air quality or minimal water pollution
        return new ArrayList<>();
    }
}
