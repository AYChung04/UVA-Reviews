package edu.virginia.sde.reviews;

import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class CourseReviewSceneStage {
    private final Stage stage;
    private final Course course;
    private CourseReviewController controller;

    public CourseReviewSceneStage(Course course) {
        stage = new Stage();
        this.course = course;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-review-scene.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            controller = fxmlLoader.getController();
            controller.setCurrentStage(stage);
            controller.setUpCourse(course);
            stage.setTitle("Reviews For Course");
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void show() {stage.show();}
    public void close() {stage.close();}

    public Course getCourse() {
        return course;
    }
}
