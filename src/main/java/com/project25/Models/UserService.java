package com.project25.Models;

import com.project25.Exceptions.UsernameTakenException;
import com.project25.Exceptions.ValidationException;

import java.sql.SQLException;

public class UserService {
    private String username;
    private String email;
    private String password;
    DatabaseDriver db;

    public UserService() {
        db = Model.getInstance().getDatabaseDriver();
    }

    public void emailInput(String email) throws ValidationException {
        Validation.validateInput(email, "email");
        this.email = email;
    }

    public void usernameInput(String username) throws ValidationException, UsernameTakenException {
        //make username taken exception
        Validation.validateInput(username, "username");
        db.isUsernameTaken(username);
        this.username = username;
    }

    public void passwordInput(String password) throws ValidationException {
        Validation.validateInput(password, "password");
        this.password = password;

    }

    public void login(String username, String password) throws SQLException, ValidationException {
        db.setUsername(username);
        db.setPassword(password);
        try {
            if (!db.fetchUserData()) {
                throw new ValidationException("Invalid username or password, try again: ");
            }
            } catch (SQLException e) {
            throw new SQLException("Database error: " + e.getMessage());
        }
        db.closeConnection();
    }

    public void register()  throws SQLException{
        db.setUsername(username);
        db.setPassword(password);
        db.setEmail(email);
        try {
            db.importUserData();
        } catch (SQLException e) {
            throw new SQLException("Database error: " + e.getMessage());
        }
        db.closeConnection();
    }

}
