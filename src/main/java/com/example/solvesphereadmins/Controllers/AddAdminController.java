package com.example.solvesphereadmins.Controllers;

import DatabaseUnit.AdminDAO;
import DatabaseUnit.AdminDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class AddAdminController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> roleComboBox;
    private final AdminDAO adminDAO = new AdminDAOImpl();

    @FXML
    public void handleAddAdmin() throws SQLException, ClassNotFoundException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim();
        String role = roleComboBox.getValue();
;
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || role == null) {
            showAlert("Missing Fields", "Please fill in all fields.");
            return;
        }

        if (adminDAO.adminExists(username, email)) {
            showAlert("Error", "Admin with this username or email already exists.");
            return;
        }

        //confirm before adding admin
        Optional<ButtonType> confirmation = showConfirmation("Add Admin", "Are you sure you want to add this admin?");
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            boolean success = adminDAO.addAdmin(username, password, email, role);
            if (success) {
                showAlert("Success", "Admin added successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Failed to add admin.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
