package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.AlertsUnit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainDashboardController {
    private Admin currentAdmin;

    @FXML private StackPane manageAdmins;
    @FXML private StackPane manageUsers;
    @FXML private StackPane managePosts;
    @FXML private StackPane manageComments;
    @FXML private StackPane manageLogs;

        public void init(Admin passedAdmin){
        this.currentAdmin = passedAdmin;
        List<StackPane> cards = List.of(manageAdmins, manageUsers, managePosts, manageComments,manageLogs);

        // shadow effect
        DropShadow shadowEffect = new DropShadow();
        shadowEffect.setColor(Color.web("#3498db")); // Blue shadow
        shadowEffect.setRadius(10);
        shadowEffect.setSpread(0.2);

        for (StackPane card : cards) {
            card.setOnMouseEntered(event -> applyHoverEffect(card, shadowEffect));
            card.setOnMouseExited(event -> removeHoverEffect(card));
        }
    }

    private void applyHoverEffect(StackPane card, DropShadow shadowEffect) {
        card.setEffect(shadowEffect);
        card.setScaleX(1.05);
        card.setScaleY(1.05);
    }

    private void removeHoverEffect(StackPane card) {
        card.setEffect(null);
        card.setScaleX(1.0);
        card.setScaleY(1.0);
    }

    @FXML
    private void handleManageAdmins() {
        if (currentAdmin != null && "SUPER_ADMIN".equals(currentAdmin.getRole())) {
            openManageAdminsPage();
        } else {
            AlertsUnit.showAccessDeniedAlert();
        }
    }
    private void openManageAdminsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/ManageAdmins.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Admins");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleManageUsers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/ManageUsers.fxml"));
            Parent root = loader.load();
            ManageUsersController controller = loader.getController();
            if (currentAdmin != null) {
                controller.setAdmin(currentAdmin);
                controller.initialize();
            } else {
                System.err.println("⚠️ Error: currentAdmin is NULL in handleManageUsers()");
            }

            // Create and configure the stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Users");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void handleManagePosts() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/ManagePosts.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Users");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleManageComments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/ManageComments.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Comments");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleManageLogs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/AdminLogs.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Logs");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
