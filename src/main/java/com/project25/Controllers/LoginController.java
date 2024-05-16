package com.project25.Controllers;

import atlantafx.base.util.Animations;
import com.project25.Components.User;
import com.project25.Models.Model;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    public TextField loginEmailText;
    @FXML
    public PasswordField loginPasswordText;
    @FXML
    public Button signInBtn;
    @FXML
    public Label errorLabel;
    @FXML
    public ImageView rightPhoto;
    @FXML
    public Button registerShifter;

    public LoginController() {
    }

    public void initialize() {
        registerShifter.setOnAction(e -> registerShiftClicked());
    }

    public void signInBtnOnAction() {
        String username = loginEmailText.getText();
        String password = loginPasswordText.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Platform.runLater(() -> errorLabel.setText("Username or password cannot be empty. Please try again."));
            return;
        }

        try {
            User loggedIn = Model.getInstance().getDatabaseDriver().fetchUserData(username, password);
            if (loggedIn != null) {
                Platform.runLater(() -> errorLabel.setText("Login successful!"));
                Model.getInstance().setCurrentUser(loggedIn);
                Stage stage = (Stage) errorLabel.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(stage);
                Model.getInstance().getViewFactory().showNewPost();
            } else {
                Platform.runLater(() -> errorLabel.setText("Invalid username or password. Please try again."));
            }
        } catch (SQLException e) {
            Platform.runLater(() -> errorLabel.setText("Database error: " + e.getMessage()));
        } catch (NoSuchAlgorithmException e) {
            Platform.runLater(() -> errorLabel.setText("Error hashing password: " + e.getMessage()));
        }
    }

    public void registerShiftClicked() {
        var photoShift = Animations.fadeOutLeft(rightPhoto, Duration.seconds(2));
        photoShift.playFromStart();
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showRegisterWindow();
    }
}
