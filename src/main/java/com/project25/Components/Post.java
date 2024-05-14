package com.project25.Components;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private int ID;
    private Image postImage;
    private LocalDate creationTime;
    private String content;
    private User author;
    private final List<Comment> comments;
    private final List<Like> likes;
    private final List<Dislike> dislikes;

    public Post(int ID, Image postImage, String content, User author, LocalDate creationTime) {
        this.ID = ID;
        this.postImage = postImage;
        this.creationTime = creationTime;
        this.content = content;
        this.author = author;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.dislikes = new ArrayList<>();
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
    public void removeLike(Like like) {
        likes.remove(like);
    }
    public Image getPostImage() {
        return postImage;
    }

    public void setPostImage(Image postImage) {
        this.postImage = postImage;
    }
    public List<Dislike> getDislikes() {
        return dislikes;
    }

    public void addDisLike(Dislike dislike) {
        dislikes.add(dislike);
    }
    public void removeDisLike(Dislike dislike) {
        dislikes.remove(dislike);
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
