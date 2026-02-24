package data;

import model.PollutionFilter;
import model.PollutionReportModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLPollutionDAO implements IPollutionDAO {

    @Override
    public List<PollutionReportModel> getAllReports(){
        String sql = "SELECT * FROM cities_pollution";

        List<PollutionReportModel> reports = new ArrayList<>();
        try(Connection conn = DatabaseConnector.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                reports.add(new PollutionReportModel(
                        rs.getString("\"Country\""),
                        rs.getString("City"),
                        rs.getString("\"Region\""),
                        rs.getDouble("\"AirQuality\""),
                        rs.getDouble("\"WaterPollution\"")
                ));
            }

            return reports;
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't load max air quality reports");
        }
    }

    @Override
    public List<PollutionReportModel> getMaxAirQuality() {
        String sql = "SELECT * FROM cities_pollution " +
                "WHERE `\"AirQuality\"`= (SELECT MAX(`\"AirQuality\"`) FROM cities_pollution)";

        List<PollutionReportModel> reports = new ArrayList<>();
        try(Connection conn = DatabaseConnector.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                reports.add(new PollutionReportModel(
                        rs.getString("\"Country\""),
                        rs.getString("City"),
                        rs.getString("\"Region\""),
                        rs.getDouble("\"AirQuality\""),
                        rs.getDouble("\"WaterPollution\"")
                ));
            }

            return reports;
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't load max air quality reports");
        }
    }

    @Override
    public List<PollutionReportModel> getMaxWaterPollution() {
        String sql = "SELECT * FROM cities_pollution " +
                "WHERE `\"WaterPollution\"`= (SELECT MAX(`\"WaterPollution\"`) FROM cities_pollution)";

        List<PollutionReportModel> reports = new ArrayList<>();
        try(Connection conn = DatabaseConnector.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                reports.add(new PollutionReportModel(
                        rs.getString("\"Country\""),
                        rs.getString("City"),
                        rs.getString("\"Region\""),
                        rs.getDouble("\"AirQuality\""),
                        rs.getDouble("\"WaterPollution\"")
                ));
            }

            return reports;
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't load max water pollution reports");
        }
    }

    @Override
    public List<PollutionReportModel> findReports(PollutionFilter filter) {
        List<PollutionReportModel> reports = new ArrayList<>();

        // 1=1 is a dummy condition that is always true.
        // It allows us to safely append "AND ..." clauses later without worrying if it's the first condition.
        StringBuilder sql = new StringBuilder("SELECT * FROM cities_pollution WHERE 1=1");

        // Use a list of Objects to hold parameters dynamically so we can mix Strings and Doubles
        List<Object> params = new ArrayList<>();

        String term = filter.getSearchTerm();
        if(term != null && !term.trim().isEmpty()) {
            // Use (0=1 OR ...) as a safe base to chain dynamic OR conditions for the search scopes
            sql.append(" AND (0=1");
            String likePattern = "%" + term.trim() + "%";

            if(filter.isSearchInCity()) {
                sql.append(" OR City LIKE ?");
                params.add(likePattern);
            }
            if(filter.isSearchInCountry()) {
                sql.append(" OR `\"Country\"` LIKE ?");
                params.add(likePattern);
            }
            if(filter.isSearchInRegion()) {
                sql.append(" OR `\"Region\"` LIKE ?");
                params.add(likePattern);
            }

            sql.append(")");
        }

        sql.append(" AND `\"AirQuality\"` >= ? AND `\"AirQUality\"` <= ?");
        params.add(filter.getMinAirQuality());
        params.add(filter.getMaxAirQuality());

        sql.append(" AND `\"WaterPollution\"` >= ? AND `\"WaterPollution\"` <= ?");
        params.add(filter.getMinWaterPollution());
        params.add(filter.getMaxWaterPollution());

        try(Connection conn = DatabaseConnector.getConnection()) {
            // Use PreparedStatement instead of simple String concatenation
            // to automatically sanitize inputs and prevent SQL Injection attacks.
            PreparedStatement stmt = conn.prepareStatement(sql.toString());

            // Dynamically inject the parameters into the query's '?' placeholders
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reports.add(new PollutionReportModel(
                        rs.getString("\"Country\""),
                        rs.getString("City"),
                        rs.getString("\"Region\""),
                        rs.getDouble("\"AirQuality\""),
                        rs.getDouble("\"WaterPollution\"")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error searching reports");
        }

        return reports;
    }

    @Override
    public List<String> findDistinctCities(String partialName, int limit) {
        return List.of();
    }
}
