package data;

import model.PollutionReportModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPollutionDAO implements IPollutionDAO {

    @Override
    public List<PollutionReportModel> getMaxPollutionReports() {
        String sql = "(SELECT * FROM cities_pollution " +
                "WHERE `\"AirQuality\"` = " +
                "(SELECT MIN(`\"AirQuality\"`) FROM cities_pollution))" +
                "UNION (SELECT * FROM cities_pollution " +
                "WHERE `\"WaterPollution\"` = " +
                "(SELECT MAX(`\"WaterPollution\"`) FROM cities_pollution))";

        List<PollutionReportModel> pollutionReports = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                pollutionReports.add(new PollutionReportModel(
                        rs.getString("\"Country\""),
                        rs.getString("\"Region\""),
                        rs.getString("City"),
                        rs.getDouble("\"AirQuality\""),
                        rs.getDouble("\"WaterPollution\"")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pollutionReports;
    }

    @Override
    public List<PollutionReportModel> getMinPollutionReports() {
        List<PollutionReportModel> pollutionReports = new ArrayList<>();

        String sql = String.format(
                "(SELECT * FROM cities_pollution " +
                        "WHERE `\"AirQuality\"` = " +
                        "(SELECT MAX(`\"AirQuality\"`) from cities_pollution)) " +
                        "UNION (SELECT * FROM cities_pollution " +
                        "WHERE `\"WaterPollution\"` = " +
                        "(SELECT MIN(`\"WaterPollution\"`) FROM cities_pollution))"
        );

        try(Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pollutionReports.add(new PollutionReportModel(
                        rs.getString("\"Country\""),
                        rs.getString("\"Region\""),
                        rs.getString("City"),
                        rs.getDouble("\"AirQuality\""),
                        rs.getDouble("\"WaterPollution\"")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pollutionReports;
    }

    @Override
    public List<String> getCityByAirRange(double min, double max) {
        String sql = "SELECT City FROM cities_pollution " +
                "WHERE `\"AirQuality\"` >= ? " +
                "AND `\"AirQuality\"` <= ?";

        List<String> reports = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, min);
            statement.setDouble(2, max);

            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                reports.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

    @Override
    public int getReportByRangeCount(float min, float max) {
        return 0;
    }
}
