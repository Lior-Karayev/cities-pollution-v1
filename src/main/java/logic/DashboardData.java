package logic;

import javafx.scene.chart.XYChart;
import model.Pollution;

import java.util.ArrayList;
import java.util.List;

public class DashboardData {
    private final List<Pollution> pollutionList = new ArrayList<>();
    private final List<XYChart.Data<Number, Number>> chartData = new ArrayList<>();

    public void addPollution(Pollution p) {
        pollutionList.add(p);
    }

    public void addChartPoint(String cityName, double air, double water) {
        XYChart.Data<Number, Number> point = new XYChart.Data<>(air, water);
        point.setExtraValue(cityName);

        chartData.add(point);
    }

    public List<Pollution> getPollutionList() { return pollutionList; }
    public List<XYChart.Data<Number, Number>> getChartData() { return chartData; }
}
