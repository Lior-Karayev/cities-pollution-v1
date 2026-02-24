package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import logic.DashboardData;
import logic.PollutionManager;
import model.PollutionReportModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainController {

    // --- Sidebar Controls ---
    @FXML private Button btnConnectSql;

    // --- Navigation Controls ---
    @FXML private Button navDashboard;
    @FXML private Button navTable;

    // --- Output Areas ---
    @FXML private Label lblStatus;
    @FXML private StackPane contentArea;

    private PollutionManager pollutionManager;
    private DashboardData currentData;

    @FXML
    public void initialize() {
        this.pollutionManager = new PollutionManager();
    }

    // --- ACTIONS ---

    @FXML
    private void handleConnectSql() {
        setNavigationEnabled(true, true); // Enable ALL
        lblStatus.setText("Status: Connected to MySQL");

        this.currentData = pollutionManager.loadInitialData();
        showDashboardView();
    }

    @FXML
    private void showDashboardView() {
        if(currentData == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashboardView.fxml"));
            Parent dashboardRoot = loader.load();
            DashboardController controller = loader.getController();

            // Clear previous view and immediately show the dashboard layout (acts as a loading state)
            contentArea.getChildren().clear();
            contentArea.getChildren().add(dashboardRoot);

            // Use CompletableFuture to run database queries on background daemon threads.
            // This prevents the JavaFX Application Thread from freezing during heavy I/O operations.
            CompletableFuture<List<PollutionReportModel>> airTask =
                    CompletableFuture.supplyAsync(() -> pollutionManager.getMaxAirQuality());
            CompletableFuture<List<PollutionReportModel>> waterTask =
                    CompletableFuture.supplyAsync(() -> pollutionManager.getMaxWaterPollution());

            // Combine the results when both tasks are finished
            airTask.thenCombine(waterTask, (maxAir, maxWater) -> {
                // Platform.runLater safely pushes this update back to the UI thread.
                Platform.runLater(() -> {
                    controller.updateData(currentData, maxAir, maxWater);
                });

                return null;
            }).exceptionally(ex -> {
                // Gracefully handle any SQL or connection exceptions from the background threads
                ex.printStackTrace();
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showTableView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllReportsView.fxml"));
            Parent tableViewRoot = loader.load();

            AllReportsController controller = loader.getController();
            controller.setPollutionManager(this.pollutionManager);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(tableViewRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper to lock/unlock buttons based on the mode.
     */
    private void setNavigationEnabled(boolean basic, boolean advanced) {
        navDashboard.setDisable(!basic);
        navTable.setDisable(!basic);
    }
}