package data;

import model.PollutionFilter;
import model.PollutionReportModel;

import java.util.List;

public interface IPollutionDAO {
    List<PollutionReportModel> getMaxAirQuality();
    List<PollutionReportModel> getMaxWaterPollution();
    List<PollutionReportModel> getAllReports();

    List<PollutionReportModel> findReports(PollutionFilter filter);

    List<String> findDistinctCities(String partialName, int limit);
}
