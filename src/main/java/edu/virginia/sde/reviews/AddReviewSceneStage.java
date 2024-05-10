package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddReviewSceneStage {
    private final Stage stage;
    private AddReviewController controller;

    public AddReviewSceneStage(CourseReviewController openReviewController) {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-review-scene.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            controller = fxmlLoader.getController();
            controller.setCurrentStage(stage);
            controller.setOpenReviewController(openReviewController);
            stage.setTitle("Add New Review");
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
