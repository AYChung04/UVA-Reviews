package edu.virginia.sde.reviews;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CourseReviewController implements Initializable {

    @FXML
    private TableView<Review> reviewsTable;
    @FXML
    private TableColumn<Review, SimpleStringProperty> timestampColumn;
    @FXML
    private TableColumn<Review, Integer> ratingColumn;
    @FXML
    private TableColumn<Review, SimpleStringProperty> commentColumn;
    @FXML
    private Label courseInfoLabel;
    @FXML
    private Label averageRatingLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Button addNewReviewButton;

    private ObservableList<Review> reviews;
    private Stage currentStage;
    private Course course;
    private CourseReviewHelper reviewHelper;
    private UserSession userSession;

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setUpCourse(Course course) throws SQLException {
        this.course = course;
        reviewHelper = new CourseReviewHelper();
        updateReviews();
        courseInfoLabel.setText(course.toString());
        averageRatingLabel.setText("Average Rating: " + course.getAverageRatingAsString());
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        errorLabel.setText("");
        reviewsTable.setItems(reviews);
        userSession = UserSession.getInstance();
    }

    @FXML
    private void handleBackToSearchButton() {
        SearchSceneStage searchSceneStage = new SearchSceneStage();
        searchSceneStage.show();
        currentStage.close();
    }

    public void handleEdit() throws SQLException {
        errorLabel.setText("");
        if (!reviewHelper.checkAlreadyReviewed(userSession.getUserId(), course.getCourseId())) {
            errorLabel.setText("You have not made a review for this course.");
        }
        else {
            EditReviewSceneStage editReviewSceneStage = new EditReviewSceneStage(this);
            editReviewSceneStage.show();
        }
    }

    public void handleAddNewReview() throws SQLException {
        errorLabel.setText("");
        if (reviewHelper.checkAlreadyReviewed(userSession.getUserId(), course.getCourseId())) {
            errorLabel.setText("You have already reviewed this course.");
        }
        else {
            AddReviewSceneStage addReviewSceneStage = new AddReviewSceneStage(this);
            addReviewSceneStage.show();
        }
    }

    public void updateReviews() throws SQLException {
        reviews = reviewHelper.getReviewsFromCourseID(course.getCourseId());
        reviewsTable.setItems(reviews);
    }


}
