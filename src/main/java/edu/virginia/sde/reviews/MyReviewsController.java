package edu.virginia.sde.reviews;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyReviewsController implements Initializable {
    private Stage currentStage;
    private Scene scene;
    private Parent root;

    @FXML private Label myReviewsLabel;

    @FXML private TableView<Review> reviewsTable;
    @FXML private TableColumn<Review, String> courseColumn;
    @FXML private TableColumn<Review, Integer> ratingColumn;
    @FXML private TableColumn<Review, String> commentColumn;

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username = UserSession.getInstance().getUsername();
        myReviewsLabel.setText(username + "'s Reviews");

        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        loadUserReviews();

        reviewsTable.setRowFactory(tv -> {
            TableRow<Review> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Review rowData = row.getItem();
                   //to implement
                    openCourseReview(rowData.getCourse());
                }
            });
            return row;
        });
    }

    private void loadUserReviews() {
        DbDriver dbDriver = new DbDriver();
        try {
            dbDriver.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            int userId = UserSession.getInstance().getUserId();
            ObservableList<Review> reviews = dbDriver.getReviewsByUserId(userId);
            dbDriver.disconnect();
            reviewsTable.setItems(reviews);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("search-scene.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) reviewsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            switchBackToSearchStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchBackToSearchStage(){
        SearchSceneStage search = new SearchSceneStage();
        currentStage.close();
        search.show();
    }
    private void openCourseReview(Course course) {
        CourseReviewSceneStage reviewsStage = new CourseReviewSceneStage(course);
        currentStage.close();
        reviewsStage.show();
    }


}
