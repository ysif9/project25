package com.project25.Models;

import java.time.LocalDate;

public class Comment extends Interactions{
protected String content;



    public Comment(int id, LocalDate creationTime, String content) {
        super(id, creationTime);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
