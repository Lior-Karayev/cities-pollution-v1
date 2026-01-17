package data;

import model.AirPollution;
import model.Pollution;
import model.PollutionReportModel;
import model.WaterPollution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPollutionDAO implements IPollutionDAO {

    @Override
    public List<Pollution> getMaxPollutionReports() {
        String sql = "(SELECT *, 'AIR_TYPE' as result_type FROM cities_pollution " +
                "WHERE `\"AirQuality\"` = " +
                "(SELECT MIN(`\"AirQuality\"`) FROM cities_pollution))" +
                "UNION (SELECT *, 'WATER_TYPE' as result_type FROM cities_pollution " +
                "WHERE `\"WaterPollution\"` = " +
                "(SELECT MAX(`\"WaterPollution\"`) FROM cities_pollution))";

        List<Pollution> pollutionReports = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                String type = rs.getString("result_type");
                String country = rs.getString("\"Country\"");
                String region = rs.getString("\"Region\"");
                String city = rs.getString("City");

                if("AIR_TYPE".equals(type)) {
                    double val = rs.getDouble("\"AirQuality\"");
                    pollutionReports.add(new AirPollution(
                            country,
                            city,
                            region,
                            val
                    ));
                } else if("WATER_TYPE".equals(type)) {
                    double val = rs.getDouble("\"WaterPollution\"");
                    pollutionReports.add(new WaterPollution(
                            country,
                            city,
                            region,
                            val
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pollutionReports;
    }

    @Override
    public List<Pollution> getMinPollutionReports() {
        List<Pollution> pollutionReports = new ArrayList<>();

        String sql = String.format(
                "(SELECT *, 'AIR_TYPE' as result_type FROM cities_pollution " +
                        "WHERE `\"AirQuality\"` = " +
                        "(SELECT MAX(`\"AirQuality\"`) from cities_pollution)) " +
                        "UNION (SELECT *, 'WATER_TYPE' as result_type FROM cities_pollution " +
                        "WHERE `\"WaterPollution\"` = " +
                        "(SELECT MIN(`\"WaterPollution\"`) FROM cities_pollution))"
        );

        try(Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("result_type");
                String country = rs.getString("\"Country\"");
                String region = rs.getString("\"Region\"");
                String city = rs.getString("City");

                if("AIR_TYPE".equals(type)){
                    double val = rs.getDouble("\"AirQuality\"");
                    pollutionReports.add(new AirPollution(
                            country,
                            city,
                            region,
                            val
                    ));
                } else if("WATER_TYPE".equals(type)){
                    double val = rs.getDouble("\"WaterPollution\"");
                    pollutionReports.add(new WaterPollution(
                            country,
                            city,
                            region,
                            val
                    ));
                }
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
