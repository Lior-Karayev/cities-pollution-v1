package view;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import logic.PollutionManager;
import model.PollutionFilter;
import model.PollutionReportModel;

import java.util.List;

public class AllReportsController {
    @FXML private TextField txtSearch;
    @FXML private CheckBox chkCity;
    @FXML private CheckBox chkCountry;
    @FXML private CheckBox chkRegion;

    @FXML private TableView<PollutionReportModel> tableView;
    @FXML private TableColumn<PollutionReportModel, String> colCountry;
    @FXML private TableColumn<PollutionReportModel, String> colCity;
    @FXML private TableColumn<PollutionReportModel, String> colRegion;
    @FXML private TableColumn<PollutionReportModel, Double> colAir;
    @FXML private TableColumn<PollutionReportModel, Double> colWater;

    @FXML private TextField txtMinAir;
    @FXML private TextField txtMaxAir;
    @FXML private TextField txtMinWater;
    @FXML private TextField txtMaxWater;

    private PollutionManager pollutionManager;
    private PauseTransition searchDebounce;

    public void setPollutionManager(PollutionManager pollutionManager) {
        this.pollutionManager = pollutionManager;
        triggerSearch();
    }

    @FXML
    public void initialize() {
        // OPTIMIZATION: The Debouncer
        // Instead of querying the database on every single keystroke (which causes lag),
        // this PauseTransition waits for 300ms of inactivity before firing the search.
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        colAir.setCellValueFactory(new PropertyValueFactory<>("airQuality"));
        colWater.setCellValueFactory(new PropertyValueFactory<>("waterPollution"));

        searchDebounce = new PauseTransition(Duration.millis(300));
        searchDebounce.setOnFinished(event -> triggerSearch());

        // Restart the timer from 0 whenever the user types a new character
        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            searchDebounce.playFromStart();
        });

        txtMinAir.textProperty().addListener((obs, oldVal, newVal) -> searchDebounce.playFromStart());
        txtMaxAir.textProperty().addListener((obs, oldVal, newVal) -> searchDebounce.playFromStart());
        txtMinWater.textProperty().addListener((obs, oldVal, newVal) -> searchDebounce.playFromStart());
        txtMaxWater.textProperty().addListener((obs, oldVal, newVal) -> searchDebounce.playFromStart());

        chkCity.setOnAction(e -> triggerSearch());
        chkCountry.setOnAction(e -> triggerSearch());
        chkRegion.setOnAction(e -> triggerSearch());
    }

    private void triggerSearch() {
        if(pollutionManager == null) return;

        double minAir = parseDoubleSafely(txtMinAir.getText(), 0.0);
        double maxAir = parseDoubleSafely(txtMaxAir.getText(), 1000.0);
        double minWater = parseDoubleSafely(txtMinWater.getText(), 0.0);
        double maxWater = parseDoubleSafely(txtMaxWater.getText(), 1000.0);

        PollutionFilter filter = new PollutionFilter.Builder()
                .setSearchTerm(txtSearch.getText())
                .setSearchScope(chkCity.isSelected(), chkCountry.isSelected(), chkRegion.isSelected())
                .setAirQualityRange(minAir, maxAir)
                .setWaterPollutionRange(minWater, maxWater)
                .build();

        List<PollutionReportModel> results = pollutionManager.searchReports(filter);
        tableView.setItems(FXCollections.observableArrayList(results));
    }

    private double parseDoubleSafely(String text, double defaultValue) {
        if(text == null || text.trim().isEmpty()) return defaultValue;
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
