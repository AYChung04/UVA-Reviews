package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchSceneStage {

    private final Stage stage;
    private SearchController controller;

    public SearchSceneStage() {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-scene.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            controller = fxmlLoader.getController();
            controller.setCurrentStage(stage);
            stage.setTitle("Course Reviews");
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }
}
