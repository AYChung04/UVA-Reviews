package edu.virginia.sde.reviews;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCourseController implements Initializable {

    @FXML
    private TextField addSubjectInput;
    @FXML
    private TextField addNumberInput;
    @FXML
    private TextField addTitleInput;

    private Stage currentStage;

    private SearchController openSearchController;

    private final SearchCourse searchCourse = new SearchCourse();

    public void setOpenSearchController(SearchController openSearchController) {
        this.openSearchController = openSearchController;
    }
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    public void addCourseSearch() throws SQLException {
        String subject = addSubjectInput.getText();
        int number = Integer.parseInt(addNumberInput.getText());
        String title = addTitleInput.getText();

        if (validInput(subject, number, title)) {
            SearchAddCourse addCourse = new SearchAddCourse(subject, number, title);
            searchCourse.addCourseToDB(addCourse);
        }

        addSubjectInput.clear();
        addNumberInput.clear();
        addTitleInput.clear();

        openSearchController.updateSearch();

    }
    private boolean validInput(String subjectInput, int numberInput, String titleInput){
        Alert errorType = new Alert(Alert.AlertType.ERROR);
        if(!subjectInput.matches("^[a-zA-Z]+$")  || subjectInput.length() > 4 || subjectInput.length() <= 1){

            errorType.setHeaderText("Invalid subject Mnemonic entry!");
            errorType.setContentText("Subject must only be in letter and it must be 2-4 letters long");
            errorType.showAndWait();
            return false;
        }
        if(!(numberInput >= 1000 && numberInput <= 9999 )){
            errorType.setHeaderText("Invalid course number entry!");
            errorType.setContentText("Course number must only be four digits.");
            errorType.showAndWait();
            return false;
        }
        if(titleInput.length() <= 1 || titleInput.length() > 50) {
            errorType.setHeaderText("Invalid course title entry!");
            errorType.setContentText("Title must be 1-50 characters long.");
            errorType.showAndWait();
            return false;
        }
        return true;
    }

    public void handleClose() {
        currentStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNumberInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    addNumberInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
