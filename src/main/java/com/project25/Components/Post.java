package com.project25.Components;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private int ID;
    private LocalDate creationTime;
    private String content;
    private User author;
    private final List<Comment> comments;
    private final List<Like> likes;

    public Post(int ID, String content, User author, LocalDate creationTime) {
        this.ID = ID;
        this.creationTime = creationTime;
        this.content = content;
        this.author = author;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void addLike(Like like) {
        likes.add(like);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + ID +
                ", Creationtime=" + creationTime +
                ", content='" + content + '\'' +
                ", author=" + author.getUsername() +
                ", comments=" + comments +
                ", likes=" + likes +
                '}';
    }
}
