package data;

import model.PollutionReportModel;

import java.util.List;

public interface IPollutionDAO {
    List<PollutionReportModel> getMaxPollutionCountry();
    List<PollutionReportModel> getMinPollutionCountry();

    List<PollutionReportModel> getMaxPollutionRegion();
    List<PollutionReportModel> getMinPollutionRegion();

    List<PollutionReportModel> getReportByRange(float min, float max);
    int getReportByRangeCount(float min, float max);
}
