package com.project25.Models;
import com.project25.Exceptions.UsernameTakenException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatabaseDriver {
        private final Connection connection; // Connection object for database connection
        private static final String DB_URL = "jdbc:sqlite:main.db"; // DatabaseDriver URL
        private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Email VARCHAR(255) NOT NULL," +
                "Username VARCHAR(255) NOT NULL," +
                "HashedPass VARCHAR(255) NOT NULL" +
                ")";
        private static final String INSERT_USER_SQL = "INSERT INTO users (Email, Username, HashedPass) VALUES (?, ?, ?)";
        private static final String CHECK_USERNAME_SQL = "SELECT Username FROM users WHERE Username = ?";
        private static final String SELECT_USER_SQL = "SELECT * FROM users WHERE Username = ? AND HashedPass = ?";
        private String email; // User email
        private String username; // Username
        private String password; // Password

        // Constructor to establish database connection
        public DatabaseDriver() throws SQLException {
            connection = DriverManager.getConnection(DB_URL); // Attempt to establish a connection to the database
            createTableIfNotExists(); // Create user table if it doesn't exist
        }

        // Setter for email
        public void setEmail(String email) {
            this.email = email;
        }

        // Setter for username
        public void setUsername(String username) {
            this.username = username;
        }

        // Setter for password
        public void setPassword(String password) {
            this.password = password;
        }

        // Method to create user table if it doesn't exist
        private void createTableIfNotExists() throws SQLException {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(CREATE_TABLE_SQL); // Execute SQL to create table if it doesn't exist
            }
        }

        // Method to import user data into the database
        public void importUserData() throws SQLException {
            addUser(); // Add user to the database
        }

        public boolean fetchUserData() throws SQLException {
            ResultSet resultSet = getUser();
            return resultSet.isBeforeFirst();

        }

        private ResultSet getUser() throws SQLException {
            PreparedStatement pstmt = connection.prepareStatement(SELECT_USER_SQL);
            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password));
            return pstmt.executeQuery();
        }

        // Method to add user to the database
        private void addUser() throws SQLException {
            String hashedPassword = hashPassword(password); // Hash the password before storing
             PreparedStatement pstmt = connection.prepareStatement(INSERT_USER_SQL);
             pstmt.setString(1, email); // Set email parameter
             pstmt.setString(2, username); // Set username parameter
             pstmt.setString(3, hashedPassword); // Set hashed password parameter
             pstmt.executeUpdate(); // Execute SQL to insert user into the database
        }

        // Method to close the database connection
        public void closeConnection() {
            try {
                connection.close(); // Close the database connection
            } catch (SQLException e) {
                System.out.println("DatabaseDriver connection error: " + e.getMessage()); // Output error message if closing connection fails
                      }
            }


    public void isUsernameTaken(String username) throws UsernameTakenException {
        try (PreparedStatement pstmt = connection.prepareStatement(CHECK_USERNAME_SQL)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    throw new UsernameTakenException("Username is taken, try again");
                } // If there's a result, username is taken
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking username: " + e.getMessage(), e);
        }
    }



    private String hashPassword(String password) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage(), e);
        }
    }


}
