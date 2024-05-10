package edu.virginia.sde.reviews;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditReviewController implements Initializable {
    @FXML
    private TextArea commentInput;
    @FXML
    private TextField ratingInput;
    @FXML
    private Label errorLabel;
    private Stage currentStage;
    private CourseReviewController openCourseReviewController;
    private UserSession userSession;
    private DbDriver driver = new DbDriver();

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setOpenReviewController(CourseReviewController reviewController) {
        openCourseReviewController = reviewController;
        try {
            driver.connect();
            Review review = driver.getReviewByUserIdAndCourseId(userSession.getUserId(), openCourseReviewController.getCourse().getCourseId());
            driver.disconnect();
            ratingInput.setText(String.valueOf(review.getRating()));
            commentInput.setText(review.getComment());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleDeleteButton() {
        try {
            driver.connect();
            driver.deleteReview(UserSession.getInstance().getUserId(), openCourseReviewController.getCourse().getCourseId());
            driver.disconnect();
            openCourseReviewController.updateReviews();
            currentStage.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleEditButton() throws SQLException {
        errorLabel.setText("");
        String comment = commentInput.getText();
        int rating = Integer.parseInt(ratingInput.getText());
        if (rating < 1 || rating > 5) {
            errorLabel.setText("Rating must be an integer from 1-5.");
        }
        else {
            driver.connect();
            driver.updateReview(userSession.getUserId(), openCourseReviewController.getCourse().getCourseId(), rating, comment);
            driver.disconnect();
            openCourseReviewController.updateReviews();
            currentStage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");

        userSession = UserSession.getInstance();
        ratingInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    ratingInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void handleBackButton() {
        currentStage.close();
    }
}
