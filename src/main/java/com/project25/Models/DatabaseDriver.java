package com.project25.Models;

import com.project25.Components.*;
import com.project25.Exceptions.UsernameTakenException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDriver {
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
    // Posts
    private static final String CREATE_POST_SQL = "CREATE TABLE IF NOT EXISTS posts (" +
            "ID INTEGER PRIMARY KEY," +
            "postImage BLOB," +
            "creationTime TEXT," +
            "content TEXT," +
            "author_username TEXT," +
            "FOREIGN KEY (author_username) REFERENCES users(username)" +
            ")";
    private static final String CREATE_COMMENTS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS comments (" +
            "id INTEGER PRIMARY KEY," +
            "creationTime TEXT," +
            "owner_username TEXT," +
            "content TEXT," +
            "post_id INTEGER," +
            "FOREIGN KEY (owner_username) REFERENCES users(username)," +
            "FOREIGN KEY (post_id) REFERENCES posts(ID)" +
            ")";
    private static final String CREATE_LIKES_TABLE_SQL = "CREATE TABLE IF NOT EXISTS likes (" +
            "id INTEGER PRIMARY KEY," +
            "creationTime TEXT," +
            "owner_username TEXT," +
            "post_id INTEGER," +
            "FOREIGN KEY (owner_username) REFERENCES users(username)," +
            "FOREIGN KEY (post_id) REFERENCES posts(ID)" +
            ")";

    // Dislikes table creation SQL (similar to Likes table)
    private static final String CREATE_DISLIKES_TABLE_SQL = "CREATE TABLE IF NOT EXISTS dislikes (" +
            "id INTEGER PRIMARY KEY," +
            "creationTime TEXT," +
            "owner_username TEXT," +
            "post_id INTEGER," +
            "FOREIGN KEY (owner_username) REFERENCES users(username)," +
            "FOREIGN KEY (post_id) REFERENCES posts(ID)" +
            ")";


    private Connection connection; // Connection object for database connection
    private String email; // User email
    private String username; // Username
    private String password; // Password


    // Constructor to establish database connection
    public DatabaseDriver() {
        try {
            connection = DriverManager.getConnection(DB_URL); // Attempt to establish a connection to the database
            createTableIfNotExists(); // Create user table if it doesn't exist
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            stmt.execute(CREATE_POST_SQL);
            stmt.execute(CREATE_COMMENTS_TABLE_SQL);
            stmt.execute(CREATE_LIKES_TABLE_SQL);
            stmt.execute(CREATE_DISLIKES_TABLE_SQL);
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

    /*
    Posts Data
    * */

    public void savePost(Post post) {
        String sql = "INSERT INTO posts (ID, postImage, creationTime, content, author_username) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, post.getID());
            pstmt.setBytes(2, imageToByteArray(post.getPostImage()));
            pstmt.setObject(3, post.getCreationTime());
            pstmt.setString(4, post.getContent());
            pstmt.setString(5, post.getAuthor().getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public byte[] imageToByteArray(Image image) {
        if (image == null) {
            return new byte[0];
        }
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public Image byteArrayToImage(byte[] byteArray) {
        if (byteArray.length > 2) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            try {
                BufferedImage bImage = ImageIO.read(inputStream);
                return SwingFXUtils.toFXImage(bImage, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public List<Post> loadAllPosts() {
        List<Post> posts = new ArrayList<>();
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM posts");

            while (rs.next()) {
                int ID = rs.getInt("ID");
                Image postImage = byteArrayToImage(rs.getBytes("postImage"));
                LocalDate creationTime = LocalDate.parse(rs.getString("creationTime"));
                String content = rs.getString("content");
                String authorUsername = rs.getString("author_username");

                User author = getUserByUsername(authorUsername);
                Post post = new Post(ID, postImage, content, author, creationTime);
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }


    /*
     * Comments Part
     * */
    public void storeComment(Comment comment, int postId) {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO comments (id, creationTime, owner_username, content, post_id) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setInt(1, comment.getId());
            pstmt.setString(2, comment.getCreationTime().toString());
            pstmt.setString(3, comment.getOwner().getUsername());
            pstmt.setString(4, comment.getContent());
            pstmt.setInt(5, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to load all comments for a specific post ID
    public List<Comment> loadCommentsForPost(int postId) {
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM comments WHERE post_id = ?")) {
            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    LocalDate creationTime = LocalDate.parse(rs.getString("creationTime"));
                    String ownerUsername = rs.getString("owner_username");
                    String content = rs.getString("content");
                    User owner = getUserByUsername(ownerUsername);
                    Comment comment = new Comment(id, creationTime, owner, content);
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    /*
     * Likes and Dislikes
     * */

    public void storeLike(Like like, int postId) {
        storeInteraction(like, postId, "likes");
    }

    public void storeDislike(Dislike dislike, int postId) {
        storeInteraction(dislike, postId, "dislikes");
    }

    public void deleteLike(int likeId) {
        deleteInteraction(likeId, "likes");
    }

    public void deleteDislike(int dislikeId) {
        deleteInteraction(dislikeId, "dislikes");
    }

    public List<Like> loadLikesForPost(int postId) {
        return loadInteractionsForPost(postId, "likes");
    }

    public List<Dislike> loadDislikesForPost(int postId) {
        return loadInteractionsForPost(postId, "dislikes");
    }

    private void storeInteraction(Interactions interaction, int postId, String tableName) {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO " + tableName + " (id, creationTime, owner_username, post_id) VALUES (?, ?, ?, ?)")) {
            pstmt.setInt(1, interaction.getId());
            pstmt.setString(2, interaction.getCreationTime().toString());
            pstmt.setString(3, interaction.getOwner().getUsername());
            pstmt.setInt(4, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private <T extends Interactions> List<T> loadInteractionsForPost(int postId, String tableName) {
        List<Interactions> interactions = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE post_id = ?")) {
            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    LocalDate creationTime = LocalDate.parse(rs.getString("creationTime"));
                    String ownerUsername = rs.getString("owner_username");
                    User owner = getUserByUsername(ownerUsername);
                    if (tableName.equals("likes")) {
                        interactions.add(new Like(id, creationTime, owner));
                    } else if (tableName.equals("dislikes")) {
                        interactions.add(new Dislike(id, creationTime, owner));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (List<T>) interactions;
    }


    private void deleteInteraction(int interactionId, String tableName) {
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?")) {
            pstmt.setInt(1, interactionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private User getUserByUsername(String username) {
        // temporary until philo commit
        Image profilePicture = new Image(String.valueOf(getClass().getResource("/Images/profile2.jpg")), 280, 280, true, false);
        return new User(0, "yousif", "yousif", "salah", "hey", profilePicture);
    }
}
