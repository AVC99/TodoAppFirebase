package com.example.todoappfirebase.model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String id;
    private String title;
    private String description;
    private Date date;
    private boolean isCompleted;
    private boolean isPinned;

    public Task(String id, String title, String description, Date date,  boolean isPinned) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.isCompleted = false;
        this.isPinned = isPinned;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isPinned() {
        return isPinned;
    }
}
