package com.example.solvesphereadmins.RetrievedUserData;

import java.util.List;

public interface UserDAO {
    List<User> geAllUsers();

    Long getUserIdByUsernameAndEmail(String username, String email);

    List<User> getAllUsers();

    void updateUserStatus(long userId, User.UserStatus status);
    void deleteUser(long userId);
}
