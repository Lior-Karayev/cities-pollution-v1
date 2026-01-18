package logic;

import data.CsvDataReader;
import data.DatabaseConnector;
import data.MySQLPollutionDAO;
import model.AirPollution;
import model.Pollution;
import model.PollutionReportModel;
import model.WaterPollution;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PollutionManager {
    private CsvDataReader csv;
    private final MySQLPollutionDAO dao;

    public PollutionManager() {
        this.dao = new MySQLPollutionDAO();
        try {
            this.csv = new CsvDataReader("data.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Couldn't find data.csv");
        }
    }

    public DashboardData loadCSVData() {
        if(csv == null) return null;
        List<String> rawLines = csv.getCsvFileLines();
        DashboardData result = new DashboardData();

        for(int i = 0; i < rawLines.size(); i++) {
            String line = rawLines.get(i);

            if(line.trim().isEmpty()) continue;

            String[] parts = line.split(",", -1);

            if(parts.length < 5) {
                System.err.println("Skipping malformed line " + i + ": " + line);
                continue;
            }

            try {

                String city = clean(parts[0]);
                String region = clean(parts[1]);
                String country = clean(parts[2]);
                String airStr = clean(parts[3]);
                String waterStr = clean(parts[4]);

                if(country.isEmpty() || city.isEmpty()) continue;

                double air = airStr.isEmpty() ? 0.0 : Double.parseDouble(airStr);
                double water = waterStr.isEmpty() ? 0.0 : Double.parseDouble(waterStr);

                result.addPollution(new AirPollution(country, city, region, air));
                result.addPollution(new WaterPollution(country, city, region, water));
                result.addChartPoint(city, air, water);
            } catch (NumberFormatException e) {
                System.err.println("Skipping invalid row: " + i);
            }
        }

        return result;
    }

    private String clean(String raw) {
        if (raw == null) return "";

        return raw.replace("\"", "").trim();
    }

    public List<String> getCSVMaxWaterPollutionCities() throws FileNotFoundException {
        CsvDataReader csv = new CsvDataReader("data.csv");
        return csv.getMaxWaterPollution();
    }

    public List<String> getCSVMinWaterPollutionCities() throws FileNotFoundException {
        CsvDataReader csv = new CsvDataReader("data.csv");
        return csv.getMinWaterPollution();
    }

    public List<String> getCSVMaxAirPollutionCities() throws FileNotFoundException {
        CsvDataReader csv = new CsvDataReader("data.csv");
        return csv.getMaxAirPollution();
    }

    public List<String> getCSVMinAirPollutionCities() throws FileNotFoundException {
        CsvDataReader csv = new CsvDataReader("data.csv");
        return csv.getMinAirPollution();
    }

    public Set<String> getMaxPollutionCountries() {
        return dao.getMaxPollutionReports().stream().map(Pollution::getCountry).collect(Collectors.toSet());
    }

    public Set<String> getMinPollutionCountries() {
        return dao.getMinPollutionReports().stream().map(Pollution::getCountry).collect(Collectors.toSet());
    }

    public Set<String> getMaxPollutionRegions() {
        return dao.getMaxPollutionReports().stream().map(Pollution::getRegion).collect(Collectors.toSet());
    }

    public Set<String> getMinPollutionRegion() {
        return dao.getMinPollutionReports().stream().map(Pollution::getRegion).collect(Collectors.toSet());
    }

    public Set<String> getCitiesByAirRange(double min, double max) throws RuntimeException {
        if(min < 0 || max < 0) {
            throw new RuntimeException("Minimum and maximum must be positive.");
        }

        if(max < min) {
            throw new RuntimeException("Minimum can't be greater then maximum.");
        }

        return new HashSet<>(dao.getCityByAirRange(min, max));
    }
}
