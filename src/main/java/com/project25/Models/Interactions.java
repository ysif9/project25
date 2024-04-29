package com.project25.Models;

import java.time.LocalDate;

public abstract class Interactions {
    private int id;
    private LocalDate creationTime;
    
    public Interactions() {
    }

    public Interactions(int id, LocalDate creationTime) {
        this.id = id;
        this.creationTime = creationTime;
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
}
