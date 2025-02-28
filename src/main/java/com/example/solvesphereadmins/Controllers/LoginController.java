package com.example.solvesphereadmins.Controllers;

import DatabaseUnit.AdminDAO;
import DatabaseUnit.AdminDAOImpl;
import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.AlertsUnit;
import com.example.solvesphereadmins.SecurityUnit.Authenticator;
import com.example.solvesphereadmins.SecurityUnit.PasswordHasher;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final AdminDAO adminDAO = new AdminDAOImpl();
    private final Authenticator authenticator = new Authenticator(new PasswordHasher());

    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        Admin admin = adminDAO.getAdminByUsername(username);

        if (admin == null) {
            AlertsUnit.showErrorAlert("User does not exist.");
            return;
        }

        if (admin.getStatus().equals("SUSPENDED")) {
            AlertsUnit.showErrorAlert("Account is suspended. Contact Support.");
            return;
        }

        if (authenticator.authenticate(username, password)) {
            System.out.println("Login Successful!");
            // Proceed to dashboard
            openDashboard(admin);
        } else {
            AlertsUnit.showErrorAlert("Invalid username or password.");
        }
    }

    private void openDashboard(Admin admin) {
        System.out.println("Opening dashboard for: " + admin.getUsername());
        // Implement scene transition logic here
    }
}
