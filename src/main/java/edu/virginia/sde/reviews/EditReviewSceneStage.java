package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EditReviewSceneStage {
    private final Stage stage;
    private EditReviewController controller;

    public EditReviewSceneStage(CourseReviewController openCourseReviewController) {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-review-scene.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            controller = fxmlLoader.getController();
            controller.setCurrentStage(stage);
            controller.setOpenReviewController(openCourseReviewController);
            stage.setTitle("Add New Course");
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
