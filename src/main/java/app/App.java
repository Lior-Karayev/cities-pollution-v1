package app;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.PollutionManager;

public class App extends Application {
    private Stage primaryStage;
    private PollutionManager manager;

    public void start(Stage stage) {
        this.primaryStage = stage;
        showSourceSelectionWindow();
        stage.setTitle("Cities Pollution Analytics");
        stage.show();
    }

    private void showSourceSelectionWindow() {

    }
}
