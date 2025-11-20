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
    public List<PollutionReportModel> getMaxPollutionCountry() {
        String sql = "(SELECT * FROM cities_pollution " +
                "WHERE \"AirQuality\" = " +
                "(SELECT MIN(\"AirQuality\") FROM cities_pollution))" +
                "UNION (SELECT * FROM cities_pollution " +
                "WHERE \"WaterPollution\" = " +
                "(SELECT MAX(\"WaterPollution\") FROM cities_pollution))";

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
    public List<PollutionReportModel> getMinPollutionCountry() {
        return List.of();
    }

    @Override
    public List<PollutionReportModel> getMaxPollutionRegion() {
        return List.of();
    }

    @Override
    public List<PollutionReportModel> getMinPollutionRegion() {
        return List.of();
    }

    @Override
    public List<PollutionReportModel> getReportByRange(float min, float max) {
        return List.of();
    }

    @Override
    public int getReportByRangeCount(float min, float max) {
        return 0;
    }
}
