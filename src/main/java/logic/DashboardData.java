package logic;

import model.PollutionReportModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardData {
    private final List<PollutionReportModel> pollutionList = new ArrayList<>();

    public void addPollution(PollutionReportModel p) {
        pollutionList.add(p);
    }

    public List<PollutionReportModel> getPollutionList() { return pollutionList; }
}
