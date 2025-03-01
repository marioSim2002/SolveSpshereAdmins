package com.example.solvesphereadmins.Controllers;

import DatabaseUnit.AdminDAO;
import DatabaseUnit.AdminDAOImpl;
import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.AlertsUnit;
import com.example.solvesphereadmins.SecurityUnit.Authenticator;
import com.example.solvesphereadmins.SecurityUnit.PasswordHasher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

        if (authenticator.authenticate(username, password) != null) {
            Admin admin = authenticator.authenticate(username, password);
            System.out.println("Login Successful!");
            System.out.println(admin.getUsername()); // success dg

            openDashboard(admin);
        } else {
            AlertsUnit.showErrorAlert("Invalid username or password.");
        }
    }

    private void openDashboard(Admin admin) {
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
