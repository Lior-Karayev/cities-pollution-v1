package logic;

import data.MySQLPollutionDAO;
import model.PollutionFilter;
import model.PollutionReportModel;
import java.util.List;

public class PollutionManager {
    private final MySQLPollutionDAO dao;

    public PollutionManager() {
        this.dao = new MySQLPollutionDAO();
    }

    public DashboardData loadInitialData() {
        DashboardData data = new DashboardData();
        List<PollutionReportModel> reports = dao.getAllReports();

        for(PollutionReportModel report: reports) {
            data.addPollution(report);
        }
        return data;
    }

    public List<PollutionReportModel> searchReports(PollutionFilter filter) {
        return dao.findReports(filter);
    }

    public List<PollutionReportModel> getMaxAirQuality() {
        return dao.getMaxAirQuality();
    }

    public List<PollutionReportModel> getMaxWaterPollution() {
        return dao.getMaxWaterPollution();
    }
}
