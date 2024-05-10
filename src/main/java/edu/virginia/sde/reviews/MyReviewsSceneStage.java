package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyReviewsSceneStage {
    private final Stage stage;
    private MyReviewsController controller;

    public MyReviewsSceneStage() {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("my-reviews-scene.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            controller = fxmlLoader.getController();
            controller.setCurrentStage(stage);
            stage.setTitle("Reviews by User");
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    public void show() {stage.show();}
    public void close() {stage.close();}
}
