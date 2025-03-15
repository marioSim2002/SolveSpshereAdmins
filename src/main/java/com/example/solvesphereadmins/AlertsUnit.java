package com.example.solvesphereadmins;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

public abstract class AlertsUnit {
    private static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.setContentText(contentText);
            alert.setResizable(false);
            alert.showAndWait();
        });
    }

    private static void showAlert(Alert.AlertType alertType, String title, String contentText) {
        showAlert(alertType, title, null, contentText);
    }
    //// specifics ////
    public static void showInvalidDataAlert() {
        showAlert(Alert.AlertType.INFORMATION, "Invalid Data!", "At least one of your info isn't correct.");
    }

    public static void successAddAlert() {
        showAlert(Alert.AlertType.INFORMATION, "Success", "Your problem has been posted\nothers can interact with it and suggest solutions.");
    }

    public static void showSuspendedAdminAlert() {
        showAlert(Alert.AlertType.INFORMATION, "Suspended", "You are suspended\ncontact super admin/s.");
    }

    public static void showSuccessRegistrationAlert() {
        showAlert(Alert.AlertType.INFORMATION, "Success", "Registered Successfully!");
    }

    public static void showSuccessLogInAlert() {
        showAlert(Alert.AlertType.INFORMATION, "Success", "User logged in successfully!");
    }

    public static void showErrorAlert(String response) {
        showAlert(Alert.AlertType.ERROR, "Error Occurred", "Error while Processing request !\n" + response);
    }

    public static void userNotRegisteredAlert() {
        showAlert(Alert.AlertType.WARNING, "User not registered", "Please register before attempting login!");
    }

    public static void commentDeletedSuccessfullyAlert() {
        showAlert(Alert.AlertType.INFORMATION, "Comment Deleted", "The comment has been deleted successfully!");
    }

    public static void commentNotFoundAlert() {
        showAlert(Alert.AlertType.ERROR, "Error", "The comment you are trying to delete does not exist.");
    }

    public static void showAccessDeniedAlert() {
        showAlert(Alert.AlertType.ERROR, "Access Denied!", "You do not have permission to access this page, Contact super-admin.");
    }

    public static void successExAlert() {
        showAlert(Alert.AlertType.INFORMATION, "Success", "File exported successfully.");
    }
}
