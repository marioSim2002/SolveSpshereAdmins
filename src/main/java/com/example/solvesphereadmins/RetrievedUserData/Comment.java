package com.example.solvesphereadmins.RetrievedUserData;

import java.sql.Timestamp;

public class Comment {
    private long id;
    private long userId;
    private long problemId;
    private String content;
    private Timestamp createdAt;
    private int upvotes;
    private int downvotes;
    private boolean isSolution; // Stored as 0 (false) or 1 (true) in DB

    public Comment(long id, long userId, long problemId, String content, Timestamp createdAt, int upvotes, int downvotes, int isSolution) {
        this.id = id;
        this.userId = userId;
        this.problemId = problemId;
        this.content = content;
        this.createdAt = createdAt;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.isSolution = (isSolution == 1); // Convert 0/1 to boolean
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

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public boolean isSolution() {
        return isSolution;
    }

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

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public void setSolution(boolean isSolution) {
        this.isSolution = isSolution;
    }

    // Convert to string for debugging
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", problemId=" + problemId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", upvotes=" + upvotes +
                ", downvotes=" + downvotes +
                ", isSolution=" + (isSolution ? "Yes" : "No") +
                '}';
    }
}
