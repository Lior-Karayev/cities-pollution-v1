package logic;

import data.CsvDataReader;
import data.DatabaseConnector;
import data.MySQLPollutionDAO;
import model.Pollution;
import model.PollutionReportModel;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PollutionManager {
    private final MySQLPollutionDAO dao;

    public PollutionManager() {
        this.dao = new MySQLPollutionDAO();
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
