package app;

import data.CsvDataReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.PollutionManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainLayout.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            primaryStage.setTitle("Cities Pollution Analytics");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(900);
            primaryStage.setMaxHeight(600);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
