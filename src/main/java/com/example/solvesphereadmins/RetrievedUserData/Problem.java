package com.example.solvesphereadmins.RetrievedUserData;

import java.sql.Timestamp;

public class Problem {
    private long id;
    private long userId;
    private String title;
    private String description;
    private String category;
    private Timestamp createdAt;
    private boolean isAgeRestricted;

    public Problem(long id, long userId, String title, String description, String category, Timestamp createdAt, boolean isAgeRestricted) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.createdAt = createdAt;
        this.isAgeRestricted = isAgeRestricted;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public boolean isAgeRestricted() {
        return isAgeRestricted;
    }

    // setters
    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setAgeRestricted(boolean ageRestricted) {
        isAgeRestricted = ageRestricted;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", isAgeRestricted=" + isAgeRestricted +
                '}';
    }
}
