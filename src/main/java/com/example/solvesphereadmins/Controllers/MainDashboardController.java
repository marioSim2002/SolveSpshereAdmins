package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.AdminUnit.Admin;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class MainDashboardController {
    Admin currentAdmin;

    @FXML private StackPane manageAdmins;
    @FXML private StackPane manageUsers;
    @FXML private StackPane managePosts;
    @FXML private StackPane manageComments;



    public void init(Admin currentAdmin){
        this.currentAdmin = currentAdmin;
        List<StackPane> cards = List.of(manageAdmins, manageUsers, managePosts, manageComments);

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

    public void handleManageAdmins() {
    }

    public void handleManageUsers() {
    }

    public void handleManagePosts() {
    }

    public void handleManageComments() {
    }
}
