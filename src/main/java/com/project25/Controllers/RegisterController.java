package com.project25.Controllers;

import com.project25.Models.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    public TextField registerEmailText;
    @FXML
    public TextField registerUsernameText;
    @FXML
    public PasswordField registerPasswordText;
    @FXML
    public PasswordField registerPasswordText2;
    @FXML
    public Label errorLabel;
    @FXML
    public Button signUpBtn;
    public Button registerShifter;

    @FXML
    public void signUpBtnOnAction(ActionEvent event) {
        String email = registerEmailText.getText();
        String username = registerUsernameText.getText();
        String password = registerPasswordText.getText();
        String password2 = registerPasswordText2.getText();

        if (email.isBlank() || username.isBlank() || password.isBlank()) {
            errorLabel.setText("Some field(s) are empty.");
        }
        if (!password.equals(password2)) {
            errorLabel.setText("Passwords don't match. Please try again.");
            return;
        }

        if (!isValidEmail(email)) {
            errorLabel.setText("Invalid email format. Please enter a valid email address.");
            return;
        }

        if (!isValidUsername(username)) {
            errorLabel.setText("Invalid username format. Usernames must consist of at least 6 alphanumeric characters and underscores only.");
            return;
        }

        if (!isValidPassword(password)) {
            errorLabel.setText("Invalid password format. Password must be at least 8 characters and contain a lowercase letter, uppercase letter, number, and special character.");
            return;
        }

        if (Model.getInstance().getDatabaseDriver().isUsernameTaken(username)) {
            errorLabel.setText("Username is already taken. Please choose a different username.");
            return;
        }
        try {
            Model.getInstance().getDatabaseDriver().addUser(username, email, password, new Image(String.valueOf(getClass().getResource("/Images/profile.jpg"))));
            errorLabel.setText("Registration successful!");
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting user: " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage(), e);
        }

    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]{6,}$");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerShifter.setOnMouseClicked(e -> loginShiftClicked());
    }

    private void loginShiftClicked() {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
