package com.example.solvesphereadmins.AdminUnit;

import com.example.solvesphereadmins.SolveShereDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AdminActionLogger {
    public static void logAdminAction(int adminId, String actionType, Integer targetId, String targetType, String description) {
        String query = "INSERT INTO admin_actions (admin_id, action_type, target_id, target_type, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, adminId);
            stmt.setString(2, actionType);
            if (targetId != null) {
                stmt.setInt(3, targetId);
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setString(4, targetType);
            stmt.setString(5, description);

            stmt.executeUpdate();
            System.out.println("Admin action logged: " + description);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
