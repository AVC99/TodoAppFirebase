package com.example.todoappfirebase.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Task implements Serializable {
    private String id;
    private String title;
    private String description;
    private Date dueDate;
    private boolean isCompleted;
    private String userID;


    public Task(String id, String title, String description, Date dueDate, boolean isCompleted, String userID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.userID = userID;

    }

    public boolean isCompleted() {
        return isCompleted;
    }



    public Task() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
