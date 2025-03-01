package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.AdminUnit.Admin;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class MainDashboardController {
    Admin currentAdmin;
    @FXML
    private StackPane manageAdmins;


    public void init(Admin currentAdmin){
        this.currentAdmin = currentAdmin;
    }
    public void handleManageAdmins() {
    }

    public void handleManageUsers() {
    }

    public void handleManagePosts() {
    }

    public void handleManageComments() {
    }
}
