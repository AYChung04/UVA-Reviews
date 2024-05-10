package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSceneStage {

    private final Stage stage;
    private LoginController controller;

    public LoginSceneStage() {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-scene.fxml"));
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