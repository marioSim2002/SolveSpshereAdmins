package com.example.solvesphereadmins.Controllers;

import DatabaseUnit.AdminDAO;
import DatabaseUnit.AdminDAOImpl;
import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.AdminUnit.SessionManager;
import com.example.solvesphereadmins.AlertsUnit;
import com.example.solvesphereadmins.SecurityUnit.Authenticator;
import com.example.solvesphereadmins.SecurityUnit.PasswordHasher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePasswordField;
    @FXML
    private CheckBox showPasswordCheckbox;


    private final AdminDAO adminDAO = new AdminDAOImpl();
    private final Authenticator authenticator = new Authenticator(new PasswordHasher());

    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        Admin admin = authenticator.authenticate(username, password);

        if (admin != null) {
            System.out.println("Login Successful!");
            System.out.println("Logged in as: " + admin.getUsername());
            SessionManager.setCurrentAdmin(admin);
            openDashboard(admin);
        } else {
            AlertsUnit.showErrorAlert("Invalid username or password.");
        }
    }

    private void openDashboard(Admin admin) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/MainDashboard.fxml"));
            Parent root = loader.load();

            MainDashboardController controller = loader.getController();
            controller.init(admin);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
            stage.show();

            // Close the login window (optional)
            ((Stage) usernameField.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void togglePasswordVisibility() {
        if (showPasswordCheckbox.isSelected()) {
            visiblePasswordField.setText(passwordField.getText());
            visiblePasswordField.setVisible(true);
            passwordField.setVisible(false);
        } else {
            passwordField.setText(visiblePasswordField.getText());
            passwordField.setVisible(true);
            visiblePasswordField.setVisible(false);
        }
    }
}
