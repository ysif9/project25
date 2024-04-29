package com.project25.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private String profilePictureURL;
    private final List<Post> posts;
    private final List<User> followers;
    private final List<User> following;

    public User(int id, String username, String firstName, String lastName, String bio, String profilePictureURL) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.profilePictureURL = profilePictureURL;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void newPost(String caption) {
       this.posts.add(new Post(posts.size(), caption, this, LocalDate.now()));
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bio='" + bio + '\'' +
                ", profilePictureURL='" + profilePictureURL + '\'' +
                ", posts=" + posts +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
}
