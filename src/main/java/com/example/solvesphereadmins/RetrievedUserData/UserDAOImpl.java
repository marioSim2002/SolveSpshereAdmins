package com.example.solvesphereadmins.RetrievedUserData;

import com.example.solvesphereadmins.SolveShereDBConnection;
import com.example.solvesphereadmins.RetrievedUserData.User.UserStatus;
import sqlQueries.UserQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    // data access obj for user operations  //


    @Override
    public Long getUserIdByUsernameAndEmail(String username, String email) {
        Long userId = null;
        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UserQueries.SELECT_USER_ID_BY_USERNAME_AND_EMAIL)) {

            stmt.setString(1, username);
            stmt.setString(2, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getLong("id");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return userId;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UserQueries.GET_ALL_USERS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                //Retrieve ACTIVE column as a String
                String statusString = rs.getString("ACTIVE");
                UserStatus status;

                //convert string to enum
                switch (statusString.toUpperCase()) {
                    case "ACTIVE":
                        status = UserStatus.ACTIVE;  //enum
                        break;
                    case "BANNED":
                        status = UserStatus.BANNED;
                        break;
                    default:
                        status = UserStatus.INACTIVE;
                        break;
                }

                users.add(new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDate("date_of_birth"),
                        rs.getString("country"),
                        rs.getDate("registration_date"),
                        rs.getBytes("profile_picture"),
                        status
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    @Override
    public void updateUserStatus(long userId, UserStatus status) {

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UserQueries.UPDATE_USER_ACTIVITY_STATUS)) {
            stmt.setString(1, status.toString()); //store status as String
            stmt.setLong(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(long userId) {

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UserQueries.DELETE_USER_BY_ID)) {

            stmt.setLong(1, userId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User with ID " + userId + " was successfully deleted.");
            } else {
                System.out.println("User deletion failed. User not found.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


