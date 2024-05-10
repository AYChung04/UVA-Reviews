package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {
    @FXML
    private TextField createUsernameInput;
    @FXML
    private TextField createPasswordInput;
    @FXML
    private TextField reTypePasswordInput;
    @FXML
    private Button backButton;
    @FXML
    private Button createUserButton;
    @FXML
    private Label createUserErrorMessage;
    @FXML
    private ImageView imageView2;

    private Stage currentStage;

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/UVALogo.jpg");
        Image image = new Image(file.toURI().toString());
        imageView2.setImage(image);
    }


    public void handleBackButton() throws IOException {
        goBackToLoginPage();
    }

    private void goBackToLoginPage() throws IOException {
        LoginSceneStage loginSceneStage = new LoginSceneStage();
        currentStage.close();
        loginSceneStage.show();
    }


    public void handleCreateNewUserButton() throws SQLException {
        createUserErrorMessage.setText("");

        UsernameValidator usernameValidator = new UsernameValidator();
        PasswordValidator passwordValidator = new PasswordValidator();

        DbDriver dbDriver = new DbDriver();

        String username = createUsernameInput.getText();
        String password = createPasswordInput.getText();
        String retypePassword = reTypePasswordInput.getText();

        try {
            createUserErrorMessage.setText("");
            usernameValidator.validateUsername(username);
            passwordValidator.validatePassword(password);
            passwordValidator.checkPasswordMatch(password, retypePassword);
            dbDriver.connect();
            dbDriver.registerUser(username, password);
            dbDriver.disconnect();
            goBackToLoginPage();
        } catch (IllegalArgumentException e) {
            createUserErrorMessage.setText(e.getMessage());
            createUserErrorMessage.setStyle("-fx-text-fill: red");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}