package com.example.solvesphereadmins.AdminUnit;

import java.sql.Timestamp;

public class Admin {
    private int id;
    private String username;
    private String password;
    private String email;
    private Timestamp createdAt;
    private String status;
    private String role;

    public Admin(int id, String username, String password, String email , String status,String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getStatus() { return status; }

    public String getRole(){return role;}
    // Setters
    public void setStatus(String status) { this.status = status; }

    public void setId(int userId) {
        this.id = userId;
    }
}
