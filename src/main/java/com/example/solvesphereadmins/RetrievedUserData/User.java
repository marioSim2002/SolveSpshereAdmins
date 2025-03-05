package com.example.solvesphereadmins.RetrievedUserData;

import java.util.Date;

public class User {
    private long id;
    private String username;
    private String email;
    private String password;
    private Date dateOfBirth;
    private String country;
    private Date registrationDate;
    private byte[] profilePicture; // Stored as a BLOB
    private UserStatus status;
    public enum UserStatus {ACTIVE, INACTIVE, BANNED}

    public User(long id, String username, String email, String password, Date dateOfBirth, String country,
                Date registrationDate, byte[] profilePicture, UserStatus status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.registrationDate = registrationDate;
        this.profilePicture = profilePicture;
        this.status = status;
    }

    // Getters
    public long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getDateOfBirth() { return dateOfBirth.toString(); }
    public String getCountry() { return country; }
    public String getRegistrationDate() { return registrationDate.toString(); }
    public byte[] getProfilePicture() { return profilePicture; }
    public UserStatus getStatus() { return status; }

    // Setters
    public void setStatus(UserStatus status) { this.status = status; }
}