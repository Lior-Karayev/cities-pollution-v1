package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import logic.DashboardData;
import logic.PollutionManager;
import model.AirPollution;
import model.Pollution;
import model.WaterPollution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainController {

    // --- Sidebar Controls ---
    @FXML private Button btnLoadCsv;
    @FXML private Button btnConnectSql;

    // --- Navigation Controls ---
    @FXML private Button navDashboard;
    @FXML private Button navTable;
    @FXML private Button navRegional;

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
    private void handleLoadCsv() {
        System.out.println("Loading CSV...");
        lblStatus.setText("Status: Reading CSV file...");

        try {
            currentData = pollutionManager.loadCSVData();

            if(currentData.getPollutionList().isEmpty()) {
                lblStatus.setText("Error: CSV file is empty or missing.");
                return;
            }

            setNavigationEnabled(true, false);
            lblStatus.setText("Status: Loaded " + currentData.getPollutionList().size() + " records from CSV.");

            showDashboardView();
        }  catch (Exception e) {
            e.printStackTrace();
            lblStatus.setText("Error: Could not process CSV data.");
            setNavigationEnabled(false, false);
        }
    }

    private List<Pollution> findMaxAir(List<Pollution> list) {
        double maxVal = list.stream()
                .filter(p -> p instanceof AirPollution)
                .mapToDouble(Pollution::getValue)
                .max().orElse(-1);

        if(maxVal == -1) return new ArrayList<>();

        return list.stream()
                .filter(p -> p instanceof AirPollution && p.getValue() == maxVal)
                .toList();
    }

    private List<Pollution> findMaxWater(List<Pollution> list) {
        double maxVal = list.stream()
                .filter(p -> p instanceof WaterPollution)
                .mapToDouble(Pollution::getValue)
                .max().orElse(-1);

        if(maxVal == -1) return new ArrayList<>();

        return list.stream()
                .filter(p -> p instanceof WaterPollution && p.getValue() == maxVal)
                .toList();
    }

    @FXML
    private void handleConnectSql() {
        // Logic will go here...
        System.out.println("Connecting to SQL...");

        // Update UI State
        setNavigationEnabled(true, true); // Enable ALL
        lblStatus.setText("Status: Connected to MySQL");

        // TODO: Switch contentArea to Dashboard
    }

    @FXML
    private void showDashboardView() {
        if(currentData == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashboardView.fxml"));
            Parent dashboardRoot = loader.load();

            DashboardController controller = loader.getController();
            List<Pollution> maxAir = findMaxAir(currentData.getPollutionList());
            List<Pollution> maxWater = findMaxWater(currentData.getPollutionList());
            controller.updateData(currentData, maxAir, maxWater);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(dashboardRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showTableView() {
        lblStatus.setText("Showing Data Table...");
    }

    @FXML
    private void showRegionalView() {
        System.out.println("Navigating to Regional Stats...");
        // TODO: Inject Chart FXML into contentArea
    }

    /**
     * Helper to lock/unlock buttons based on the mode.
     */
    private void setNavigationEnabled(boolean basic, boolean advanced) {
        navDashboard.setDisable(!basic);
        navTable.setDisable(!basic);

        // Regional stats are only for SQL
        navRegional.setDisable(!advanced);
    }
}