package com.example.solvesphereadmins.AdminUnit;

import java.sql.Timestamp;

public class Comment {
    private long id;
    private long userId;
    private long problemId;
    private String content;
    private Timestamp createdAt;

    public Comment(long id, long userId, long problemId, String content, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.problemId = problemId;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters
    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getProblemId() {
        return problemId;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", problemId=" + problemId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
