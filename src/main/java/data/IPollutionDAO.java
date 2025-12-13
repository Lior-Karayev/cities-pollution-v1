package data;

import model.PollutionReportModel;

import java.util.List;

public interface IPollutionDAO {
    List<PollutionReportModel> getMaxPollutionReports();
    List<PollutionReportModel> getMinPollutionReports();

    List<String> getCityByAirRange(double min, double max);
    int getReportByRangeCount(float min, float max);
}
