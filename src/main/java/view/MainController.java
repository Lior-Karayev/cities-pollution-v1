package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

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

    @FXML
    public void initialize() {
        // This runs when the window opens.
        // We can set default states here if needed.
    }

    // --- ACTIONS ---

    @FXML
    private void handleLoadCsv() {
        // Logic will go here...
        System.out.println("Loading CSV...");

        // Update UI State
        setNavigationEnabled(true, false); // Enable Basic, Disable Regional
        lblStatus.setText("Status: CSV Loaded");

        // TODO: Switch contentArea to Dashboard
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
        System.out.println("Navigating to Dashboard...");
        // TODO: Inject Dashboard FXML into contentArea
    }

    @FXML
    private void showTableView() {
        System.out.println("Navigating to Table...");
        // TODO: Inject Table FXML into contentArea
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