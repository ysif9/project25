package com.project25.Components;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private Image profilePicture;
    private final List<Post> posts;
    private final List<User> followers;
    private final List<User> following;

    public User(int id, String username, Image profilePicture) {
        this.id = id;
        this.username = username;
        this.profilePicture = profilePicture;
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void newFollower(User follower) {
        this.followers.add(follower);
    }

    public List<User> getFollowing() {
        return following;
    }

    public void newFollowing(User following) {

        this.following.add(following);
        following.newFollower(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", profilePictureURL='" + profilePicture + '\'' +
                ", posts=" + posts +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
}
