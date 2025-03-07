package com.example.solvesphereadmins.AdminUnit;

import com.example.solvesphereadmins.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminActionDAOImpl implements AdminActionDAO {

    @Override
    public void logAdminAction(int adminId, String actionType, Integer targetId, String targetType, String description) {
        String query = "INSERT INTO admin_actions (admin_id, action_type, target_id, target_type, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
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
        }
    }

    @Override
    public List<AdminAction> getAllAdminActions() {
        List<AdminAction> actions = new ArrayList<>();
        String query = "SELECT * FROM admin_actions ORDER BY timestamp DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                actions.add(new AdminAction(
                        rs.getInt("id"),
                        rs.getInt("admin_id"),
                        rs.getString("action_type"),
                        rs.getInt("target_id"),
                        rs.getString("target_type"),
                        rs.getString("description"),
                        rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actions;
    }

    @Override
    public List<AdminAction> getActionsByAdminId(int adminId) {
        List<AdminAction> actions = new ArrayList<>();
        String query = "SELECT * FROM admin_actions WHERE admin_id = ? ORDER BY timestamp DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                actions.add(new AdminAction(
                        rs.getInt("id"),
                        rs.getInt("admin_id"),
                        rs.getString("action_type"),
                        rs.getInt("target_id"),
                        rs.getString("target_type"),
                        rs.getString("description"),
                        rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actions;
    }

    @Override
    public void deleteAdminAction(int actionId) {
        String query = "DELETE FROM admin_actions WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, actionId);
            stmt.executeUpdate();
            System.out.println("Admin action deleted: ID " + actionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
