package com.project25.Components;

import java.time.LocalDate;

public abstract class Interactions {
    private int id;
    private LocalDate creationTime;
    private User owner;
    private Post parentPost;
    
    public Interactions() {
    }

    public Interactions(int id, LocalDate creationTime, User owner) {
        this.id = id;
        this.creationTime = creationTime;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCreationTime() {

        return creationTime;
    }

    public void setCreationTime(LocalDate creationTime) {

        this.creationTime = creationTime;
    }

    public User getOwner() {
        return owner;
    }

    public Post getParentPost() {
        return parentPost;
    }

    public void setParentPost(Post parentPost) {
        this.parentPost = parentPost;
    }
}
