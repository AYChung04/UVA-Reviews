package edu.virginia.sde.reviews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private Button newUserButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginErrorMessage;
    @FXML
    private ImageView imageView;
    @FXML
    private Button closeButton;
    @FXML
    private PasswordField hiddenPassword;
    @FXML
    private CheckBox toggle;

    private Stage currentStage;

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/UVALogo.jpg");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        this.togglePassword(null);
    }

    public void handleNewUser() throws IOException {
        CreateUserSceneStage createUserSceneStage = new CreateUserSceneStage();
        currentStage.close();
        createUserSceneStage.show();
    }

    public void handleLogin() {
        String enteredUsername = usernameInput.getText();
        String enteredPassword = passwordValue();
        try {
            loginErrorMessage.setText("");
            UserAuthenticator userAuthenticator = new UserAuthenticator();
            userAuthenticator.authenticateUser(enteredUsername, enteredPassword);
            goToCourseSearchStage();
        } catch (Exception e) {
            loginErrorMessage.setText("Username and password do not match");
            loginErrorMessage.setStyle("-fx-text-fill: red");
        }
    }

    private String passwordValue() {
        return toggle.isSelected()?
                passwordInput.getText(): hiddenPassword.getText();
    }

    private void goToCourseSearchStage()  {
        SearchSceneStage searchSceneStage = new SearchSceneStage();
        currentStage.close();
        searchSceneStage.show();
    }

    public void handleClose() {
        currentStage.close();
    }

    @FXML
    public void togglePassword(ActionEvent event) {
        if (toggle.isSelected()) {
            passwordInput.setText(hiddenPassword.getText());
            passwordInput.setVisible(true);
            hiddenPassword.setVisible(false);
            return;
        }
        hiddenPassword.setText(passwordInput.getText());
        hiddenPassword.setVisible(true);
        passwordInput.setVisible(false);
    }


}