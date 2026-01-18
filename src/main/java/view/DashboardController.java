package view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import logic.DashboardData;
import model.AirPollution;
import model.Pollution;

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

    @FXML
    public void initialize() {
        viewSelector.setItems(FXCollections.observableArrayList(
                "Correlation (Scatter)",
                "Top 10 Worst Air Quality (Bar)"
        ));

        viewSelector.setValue("Correlation (Scatter)");

        viewSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updateChartDisplay(newVal);
        });
    }

    public void updateData(DashboardData data, List<Pollution> maxAir, List<Pollution> maxWater) {
        this.currentData = data;
        updateScorecards(maxAir, maxWater);
        updateChartDisplay(viewSelector.getValue());
    }

    public void updateScorecards(List<Pollution> maxAirList, List<Pollution> maxWaterList) {
        if(maxAirList != null && !maxAirList.isEmpty()) {
            Pollution first = maxAirList.getFirst();
            lblMaxAirValue.setText(String.format("%.2f", first.getValue()));

            if(maxAirList.size() > 1) {
                lblMaxAirCity.setText(first.getCity() + " (+" + (maxAirList.size() - 1) + ")");

                String allNames = maxAirList.stream().map(Pollution::getCity).collect(Collectors.joining("\n"));
                lblMaxAirCity.setTooltip(new Tooltip(generateLimitedList(maxAirList)));
            } else {
                lblMaxAirCity.setText(first.getCity());
                lblMaxAirCity.setTooltip(null);
            }
        } else {
            lblMaxAirCity.setText("N/A");
            lblMaxAirValue.setText("-");
        }

        if(maxWaterList != null) {
            Pollution first = maxWaterList.getFirst();
            lblMaxWaterValue.setText(String.format("%.2f", first.getValue()));

            if(maxWaterList.size() > 1) {
                lblMaxWaterCity.setText(first.getCity() + " (+" + (maxWaterList.size() - 1) + ")");
                String allNames = maxWaterList.stream().map(Pollution::getCity).collect(Collectors.joining("\n"));
                lblMaxWaterCity.setTooltip(new Tooltip(generateLimitedList(maxWaterList)));
            } else {
                lblMaxWaterCity.setText(first.getCity());
                lblMaxWaterCity.setTooltip(null);
            }
        } else {
            lblMaxWaterCity.setText("N/A");
            lblMaxWaterValue.setText("-");
        }
    }

    private String generateLimitedList(List<Pollution> list) {
        int limit = 15;

        String text = list.stream()
                .limit(limit)
                .map(Pollution::getCity)
                .collect(Collectors.joining("\n"));

        if(list.size() > limit) {
            text += "\n\n... and " + (list.size() - limit) + " others.";
        }

        return text;
    }

    private void updateChartDisplay(String mode) {
        if(currentData == null) return;

        chartContainer.getChildren().clear();

        if("Correlation (Scatter)".equals(mode)){
            chartContainer.getChildren().add(buildScatterChart());
        } else if("Top 10 Worst Air Quality (Bar)".equals(mode)){
            chartContainer.getChildren().add(buildBarChart());
        }
    }

    private ScatterChart<Number, Number> buildScatterChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Air Quality");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Water Pollution");

        ScatterChart<Number, Number> sc = new ScatterChart<>(xAxis, yAxis);
        sc.setLegendVisible(false);
        sc.setAnimated(false);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Cities");

        series.getData().addAll(currentData.getChartData());
        sc.getData().add(series);

        for(XYChart.Data<Number, Number> data : series.getData()) {
            if(data.getNode() != null) {
                String cityName = (String) data.getExtraValue();
                Tooltip.install(data.getNode(), new Tooltip(cityName + "\nAir: " + data.getXValue() + "\nWater: " + data.getYValue()));
            }
        }

        return sc;
    }

    private BarChart<String, Number> buildBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("City");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Air Pollution Score");

        // Create Chart
        BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setLegendVisible(false);
        bc.setAnimated(true);

        // Prepare Data: Sort by Air Quality Descending and take Top 10
        List<Pollution> top10 = currentData.getPollutionList().stream()
                .filter(p -> p instanceof AirPollution)
                .sorted(Comparator.comparingDouble(Pollution::getValue).reversed())
                .limit(10)
                .toList();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Pollution");

        for (Pollution p : top10) {
            series.getData().add(new XYChart.Data<>(p.getCity(), p.getValue()));
        }

        bc.getData().add(series);
        return bc;
    }
}
