package view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import logic.DashboardData;
import logic.StatisticsAggregator;
import model.PollutionReportModel;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {
    @FXML private Label lblMaxAirCity;
    @FXML private Label lblMaxAirValue;
    @FXML private Label lblMaxWaterCity;
    @FXML private Label lblMaxWaterValue;

    @FXML private ComboBox<String> viewSelector;
    @FXML private StackPane chartContainer;

    private DashboardData currentData;
    private List<PollutionReportModel> defaultMaxAir;
    private List<PollutionReportModel> defaultMaxWater;

    @FXML
    public void initialize() {
        viewSelector.setItems(FXCollections.observableArrayList(
                "Show Cities",
                "Show Country Averages",
                "Show Region Averages"
        ));

        viewSelector.setValue("Show Cities");

        viewSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updateDashboardState(newVal);
        });
    }

    public void updateData(DashboardData data, List<PollutionReportModel> maxAir, List<PollutionReportModel> maxWater) {
        this.currentData = data;
        this.defaultMaxAir = maxAir;
        this.defaultMaxWater = maxWater;

        updateScorecards(maxAir, maxWater);
        updateDashboardState(viewSelector.getValue());
    }

    public void updateScorecards(List<PollutionReportModel> maxAirList, List<PollutionReportModel> maxWaterList) {
        if(maxAirList != null && !maxAirList.isEmpty()) {
            PollutionReportModel first = maxAirList.getFirst();
            lblMaxAirValue.setText(String.format("%.2f", first.getAirQuality()));

            String displayName = getDisplayName(first);

            if(maxAirList.size() > 1) {
                lblMaxAirCity.setText(displayName + " (+" + (maxAirList.size() - 1) + ")");
                lblMaxAirCity.setTooltip(new Tooltip(generateLimitedList(maxAirList)));
            } else {
                lblMaxAirCity.setText(displayName);
                lblMaxAirCity.setTooltip(null);
            }
        } else {
            lblMaxAirCity.setText("N/A");
            lblMaxAirValue.setText("-");
        }

        if(maxWaterList != null && !maxWaterList.isEmpty()) {
            PollutionReportModel first = maxWaterList.getFirst();
            lblMaxWaterValue.setText(String.format("%.2f", first.getWaterPollution()));

            String displayName = getDisplayName(first);

            if(maxWaterList.size() > 1) {
                lblMaxWaterCity.setText(displayName + " (+" + (maxWaterList.size() - 1) + ")");
                lblMaxWaterCity.setTooltip(new Tooltip(generateLimitedList(maxWaterList)));
            } else {
                lblMaxWaterCity.setText(displayName);
                lblMaxWaterCity.setTooltip(null);
            }
        } else {
            lblMaxWaterCity.setText("N/A");
            lblMaxWaterValue.setText("-");
        }
    }

    private List<PollutionReportModel> findMaxAirInList(List<PollutionReportModel> list) {
        if(list == null || list.isEmpty()) return List.of();

        double maxAir = list.stream().mapToDouble(PollutionReportModel::getAirQuality).max().orElse(0.0);
        return list.stream().filter(p -> p.getAirQuality() == maxAir).toList();
    }

    private List<PollutionReportModel> findMaxWaterInList(List<PollutionReportModel> list) {
        if(list == null || list.isEmpty()) return List.of();

        double maxWater = list.stream().mapToDouble(PollutionReportModel::getWaterPollution).max().orElse(0.0);
        return list.stream().filter(p -> p.getWaterPollution() == maxWater).toList();
    }

    private String getDisplayName(PollutionReportModel p) {
        if("Country Average".equals(p.getCity())) {
            return p.getCountry();
        } else if("Region Average".equals(p.getCity())) {
            return p.getRegion() + ", " + p.getCountry();
        }

        return p.getCity() + ", " + p.getCountry();
    }

    private String generateLimitedList(List<PollutionReportModel> list) {
        int limit = 15;

        String text = list.stream()
                .limit(limit)
                .map(this::getDisplayName)
                .collect(Collectors.joining("\n"));

        if(list.size() > limit) {
            text += "\n\n... and " + (list.size() - limit) + " others.";
        }

        return text;
    }

    private void updateDashboardState(String mode) {
        if(currentData == null) return;

        List<PollutionReportModel> dataToDisplay;
        List<PollutionReportModel> maxAirToDisplay;
        List<PollutionReportModel> maxWaterToDisplay;

        if("Show Country Averages".equals(mode)) {
            dataToDisplay = StatisticsAggregator.getCountryAverage(currentData.getPollutionList());

            maxAirToDisplay = findMaxAirInList(dataToDisplay);
            maxWaterToDisplay = findMaxWaterInList(dataToDisplay);
        } else if("Show Region Averages".equals(mode)) {
            dataToDisplay = StatisticsAggregator.getRegionAverages(currentData.getPollutionList());
            maxAirToDisplay = findMaxAirInList(dataToDisplay);
            maxWaterToDisplay = findMaxWaterInList(dataToDisplay);
        } else {
            dataToDisplay = currentData.getPollutionList();

            maxAirToDisplay = this.defaultMaxAir;
            maxWaterToDisplay = this.defaultMaxWater;
        }

        chartContainer.getChildren().clear();
        chartContainer.getChildren().add(buildScatterChart(dataToDisplay));

        updateScorecards(maxAirToDisplay, maxWaterToDisplay);
    }

    private ScatterChart<Number, Number> buildScatterChart(List<PollutionReportModel> dataList) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Air Quality");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Water Pollution");

        ScatterChart<Number, Number> sc = new ScatterChart<>(xAxis, yAxis);
        sc.setLegendVisible(false);
        sc.setAnimated(false);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data Points");

        for(PollutionReportModel p: dataList) {
            XYChart.Data<Number, Number> point = new XYChart.Data<>(p.getAirQuality(), p.getWaterPollution());
            point.setExtraValue(p.getCountry() + " - " + p.getCity());
            series.getData().add(point);
        }

        sc.getData().add(series);

        for(XYChart.Data<Number, Number> data : series.getData()) {
            if(data.getNode() != null) {
                String cityName = (String) data.getExtraValue();
                Tooltip.install(data.getNode(), new Tooltip(cityName + "\nAir: " + data.getXValue() + "\nWater: " + data.getYValue()));
            }
        }

        return sc;
    }
}
