package data;

import model.Pollution;
import model.PollutionReportModel;

import java.util.List;

public interface IPollutionDAO {
    List<Pollution> getMaxPollutionReports();
    List<Pollution> getMinPollutionReports();

    List<String> getCityByAirRange(double min, double max);
    int getReportByRangeCount(float min, float max);
}
