package com.example.solvesphereadmins.AdminUnit;

import com.example.solvesphereadmins.DatabaseConnection;
import sqlQueries.AdminQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminActionDAOImpl implements AdminActionDAO {

    @Override
    public void logAdminAction(int adminId, String actionType, Integer targetId, String targetType, String description) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(AdminQueries.INSERT_ADMIN_ACT_SCRIPT)) {

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
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(AdminQueries.GET_ALL_ADMINS_ACTS);
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

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(AdminQueries.GET_ADMIN_ACT_BY_ID)) {

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
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(AdminQueries.DELETE_ADMIN_ACTION)) {

            stmt.setInt(1, actionId);
            stmt.executeUpdate();
            System.out.println("Admin action deleted: ID " + actionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
