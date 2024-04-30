package com.project25.Components;

import java.time.LocalDate;

public class Comment extends Interactions{
private String content;



    public Comment(int id, LocalDate creationTime, User owner,  String content) {
        super(id, creationTime, owner);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
