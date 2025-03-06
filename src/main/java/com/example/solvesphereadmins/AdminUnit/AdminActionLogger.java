package com.example.solvesphereadmins.AdminUnit;

import com.example.solvesphereadmins.SolveShereDBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

public class AdminActionLogger {

    /**
     * Shows a popup window to get the admin's description of the action.
     * If the admin provides a description, it logs the action.
     */
    public static void showPopUpWind(int adminId, String actionType, Long targetId, String targetType) {
        // Create the popup dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Admin Action Description");
        alert.setHeaderText("Provide a description for this action:");

        // Create a text area for input
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Enter action description...");
        descriptionField.setWrapText(true);

        VBox vbox = new VBox(descriptionField);
        alert.getDialogPane().setContent(vbox);

        // Show the popup and wait for user input
        Optional<ButtonType> result = alert.showAndWait();

        // If admin clicks OK, log the action
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String description = descriptionField.getText().trim();
            logAdminAction(adminId, actionType, targetId, targetType, description);
        }
    }

    /**
     * Logs the admin action in the database.
     */
    public static void logAdminAction(int adminId, String actionType, Long targetId, String targetType, String description) {
        String query = "INSERT INTO admin_actions (admin_id, action_type, target_id, target_type, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, adminId);
            stmt.setString(2, actionType);
            if (targetId != null) {
                stmt.setLong(3, targetId);
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
