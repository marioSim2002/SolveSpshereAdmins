package com.example.solvesphereadmins.RetrievedUserData;

import java.sql.Timestamp;
//// entity  ///
public class Report {
    private long id;
    private long problemId;
    private long reporterId;
    private String reportReason;
    private Timestamp createdAt;

    public Report(long id, long problemId, long reporterId, String reportReason, Timestamp createdAt) {
        this.id = id;
        this.problemId = problemId;
        this.reporterId = reporterId;
        this.reportReason = reportReason;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public long getProblemId() { return problemId; }
    public long getReporterId() { return reporterId; }
    public String getReportReason() { return reportReason; }
    public Timestamp getCreatedAt() { return createdAt; }
}
