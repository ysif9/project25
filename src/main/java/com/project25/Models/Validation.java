package com.project25.Models;

import com.project25.Exceptions.ValidationException;

public class Validation {


    // Method to validate input based on type
    public static void validateInput(String input, String type) throws ValidationException {
        switch (type) {
            case "email":
                if (!isValidEmail(input)) {
                    throw new ValidationException("Invalid email format. Please enter a valid email address.");
                }
                break;
            case "username":
                if (!isValidUsername(input)) {
                    throw new ValidationException("Invalid username format. Username can only contain alphanumeric characters and underscores.");
                }
                break;
            case "password":
                if (!isValidPassword(input)) {
                    throw new ValidationException("Invalid password format. Password must be at least 8 characters and contain a lowercase letter, uppercase letter, number, and special character.");
                }
                break;
            default:
                throw new ValidationException("Unsupported input type."); // Throw exception for unsupported input type
        }
    }

    // Method to validate email format using regex
    private static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    // Method to validate username format using regex
    private static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]+$");
    }

    // Method to validate password format using regex
    private static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }
}
