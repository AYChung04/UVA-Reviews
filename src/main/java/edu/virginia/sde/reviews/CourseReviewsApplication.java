package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CourseReviewsApplication extends Application {
    public static void main(String[] args) {
        try {
            DbDriver dbDriver = new DbDriver();
            dbDriver.connect();
            dbDriver.createNewTables();
            dbDriver.commit();
            dbDriver.disconnect();
            launch(args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) {
        LoginSceneStage loginSceneStage = new LoginSceneStage();
        loginSceneStage.show();
    }
}
