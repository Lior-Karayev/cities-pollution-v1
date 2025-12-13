package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CsvDataReader {
    private final Path filePath;

    public CsvDataReader(String filePath) throws FileNotFoundException{
        this.filePath = Paths.get(filePath);

        if(Files.notExists(this.filePath)) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
    }

    public List<String> getCsvFileLines(){
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<String> getMaxWaterPollution(){
        // Get the countries with maximum water pollution
        final double[] maxPoll = {0};
        List<String> cities = new ArrayList<>();

        try {
            Files.lines(this.filePath)
                    .skip(1)
                    .forEach(line -> {
                String[] fields = line.split(",");
                double pollVal = Double.parseDouble(fields[4]);
                if(pollVal > maxPoll[0]) {
                    cities.clear();
                    maxPoll[0] = pollVal;
                }
                if(pollVal == maxPoll[0]) {
                    cities.add(fields[0]);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error getting file lines", e);
        }

        return cities;
    }

    public List<String> getMinWaterPollution(){
        // Get the countries with minimal water pollution
        final double[] minPoll = {Double.MAX_VALUE};
        List<String> cities = new ArrayList<>();

        try {
            Files.lines(this.filePath)
                    .skip(1)
                    .forEach(line -> {
                String[] fields = line.split(",");
                double pollVal = Double.parseDouble(fields[4]);
                if(pollVal < minPoll[0]) {
                    cities.clear();
                    minPoll[0] = pollVal;
                }
                if(pollVal == minPoll[0]) {
                    cities.add(fields[0]);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error getting file lines", e);
        }

        return cities;
    }

    public List<String> getMaxAirPollution(){
        // Get the countries with maximum water pollution
        final double[] minQual = {Double.MAX_VALUE};
        List<String> cities = new ArrayList<>();

        try {
            Files.lines(this.filePath)
                    .skip(1)
                    .forEach(line -> {
                        String[] fields = line.split(",");
                        double pollVal = Double.parseDouble(fields[4]);
                        if(pollVal < minQual[0]) {
                            cities.clear();
                            minQual[0] = pollVal;
                        }
                        if(pollVal == minQual[0]) {
                            cities.add(fields[0]);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Error getting file lines", e);
        }

        return cities;
    }

    public List<String> getMinAirPollution(){
        // Get the countries with maximum water pollution
        final double[] maxQual = {0};
        List<String> cities = new ArrayList<>();

        try {
            Files.lines(this.filePath)
                    .skip(1)
                    .forEach(line -> {
                        String[] fields = line.split(",");
                        double pollVal = Double.parseDouble(fields[4]);
                        if(pollVal > maxQual[0]) {
                            cities.clear();
                            maxQual[0] = pollVal;
                        }
                        if(pollVal == maxQual[0]) {
                            cities.add(fields[0]);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Error getting file lines", e);
        }

        return cities;
    }
}
