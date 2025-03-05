package com.example.solvesphereadmins.RetrievedUserData;

import java.util.List;

public interface UserDAO {
    List<User> geAllUsers();

    List<User> getAllUsers();

    void updateUserStatus(long userId, User.UserStatus status);
}
