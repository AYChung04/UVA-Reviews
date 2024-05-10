package edu.virginia.sde.reviews;

import edu.virginia.sde.reviews.CreateUserSceneStage;
import edu.virginia.sde.reviews.UserAuthenticator;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private TextField subjectInput;
    @FXML
    private TextField numberInput;
    @FXML
    private TextField titleInput;
    @FXML
    private TableView<Course> coursesTable;
    @FXML
    private TableColumn<Course, String> subjectColumn;
    @FXML
    private TableColumn<Course, Integer> numberColumn;
    @FXML
    private TableColumn<Course, String> titleColumn;
    @FXML
    private TableColumn<Course, String> ratingColumn;
    @FXML
    private ImageView imageView;
    private ObservableList<Course> searchResults;
    private Search currentSearch;

    private Stage currentStage;
    private Scene scene;
    private Parent root;

    private final SearchResults results = new SearchResults();



    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("src/UVALogo.jpg");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        currentSearch = new Search();

        numberInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numberInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        try {
            searchResults = results.getResultsOfSearch(currentSearch);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectMnemonic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        ratingColumn.setCellValueFactory((p) -> {
            try {
                return new SimpleStringProperty(p.getValue().getAverageRatingAsString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        coursesTable.setItems(searchResults);

        coursesTable.setRowFactory(tv -> {
            TableRow<Course> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Course rowData = row.getItem();

                    //to implement
                    openCourseReview(rowData);
                }
            });
            return row;
        });
    }

    private void openCourseReview(Course course) {
        CourseReviewSceneStage reviewStage = new CourseReviewSceneStage(course);
        currentStage.close();
        reviewStage.show();
    }


    public void handleSearch() throws SQLException {
        String subject = subjectInput.getText();
        String number = numberInput.getText();
        String title = titleInput.getText();

        currentSearch = new Search(subject, number, title);
        updateSearchResults(results.getResultsOfSearch(currentSearch));

    }

    private void updateSearchResults(ObservableList<Course> courses) {
        searchResults = courses;
        coursesTable.setItems(searchResults);
        ratingColumn.setCellValueFactory((p) -> {
            try {
                return new SimpleStringProperty(p.getValue().getAverageRatingAsString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void MyReview(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("my-reviews-scene.fxml"));
        currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        currentStage.setScene(scene);
        switchToMyReview();

    }

    private void switchToMyReview(){
        MyReviewsSceneStage myReviews = new MyReviewsSceneStage();
        currentStage.close();
        myReviews.show();

    }

   public void logout(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("search-scene.fxml"));
        currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
       currentStage.setScene(scene);
       UserSession.clearSession();
       switchBackToLoginStage();
   }

    private void switchBackToLoginStage(){
        LoginSceneStage login = new LoginSceneStage();
        currentStage.close();
        login.show();
    }

    public void handleAddCourseButton() {
        AddCourseSceneStage addCourseSceneStage = new AddCourseSceneStage(this);
        addCourseSceneStage.show();
    }

    public void updateSearch() throws SQLException {
        updateSearchResults(results.getResultsOfSearch(currentSearch));
    }

    public void handleResetButton() throws SQLException {
        currentSearch = new Search();
        updateSearch();
    }


}