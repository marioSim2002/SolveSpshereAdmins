package com.example.solvesphereadmins.AdminUnit;

import java.sql.Timestamp;

public class AdminAction {
    private long id;
    private long adminId;
    private String actionType;
    private long targetId;
    private String targetType;
    private String description;
    private Timestamp timestamp;

    public AdminAction(long id, long adminId, String actionType, long targetId, String targetType, String description, Timestamp timestamp) {
        this.id = id;
        this.adminId = adminId;
        this.actionType = actionType;
        this.targetId = targetId;
        this.targetType = targetType;
        this.description = description;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public long getAdminId() { return adminId; }
    public String getActionType() { return actionType; }
    public long getTargetId() { return targetId; }
    public String getTargetType() { return targetType; }
    public String getDescription() { return description; }
    public Timestamp getTimestamp() { return timestamp; }
}
