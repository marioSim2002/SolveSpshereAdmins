package com.example.solvesphereadmins.AdminUnit;

import java.sql.Timestamp;

public class Admin {
    private int id;
    private String username;
    private String password;
    private String email;
    private Timestamp createdAt;
    private String status;

    public Admin(int id, String username, String password, String email, Timestamp createdAt, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public Timestamp getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }

    // Setters
    public void setStatus(String status) { this.status = status; }
}
