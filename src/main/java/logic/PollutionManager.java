package logic;

import data.DatabaseConnector;
import data.MySQLPollutionDAO;
import model.PollutionReportModel;

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

    public Set<String> getMaxPollutionCountries() {
        return dao.getMaxPollutionReports().stream().map(PollutionReportModel::getCountry).collect(Collectors.toSet());
    }

    public Set<String> getMinPollutionCountries() {
        return dao.getMinPollutionReports().stream().map(PollutionReportModel::getCountry).collect(Collectors.toSet());
    }

    public Set<String> getMaxPollutionRegions() {
        return dao.getMaxPollutionReports().stream().map(PollutionReportModel::getRegion).collect(Collectors.toSet());
    }

    public Set<String> getMinPollutionRegion() {
        return dao.getMinPollutionReports().stream().map(PollutionReportModel::getRegion).collect(Collectors.toSet());
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
