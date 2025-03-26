package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.AdminUnit.AdminActionLogger;
import com.example.solvesphereadmins.RetrievedUserData.User;
import com.example.solvesphereadmins.RetrievedUserData.User.UserStatus;
import com.example.solvesphereadmins.RetrievedUserData.UserDAO;
import com.example.solvesphereadmins.RetrievedUserData.UserDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;

public class UserItemController {
    @FXML
    private ImageView profileImage;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button banButton;
    @FXML
    private Button activateButton;
    @FXML
    private Button deleteUserButton;
    private ManageUsersController parentController; // parent controller

    private final UserDAO userDAO = new UserDAOImpl();
    private User user;
    private Admin currentAdmin;

    public void setUser(User user, ManageUsersController parentController, Admin admin) {
        this.user = user;
        this.parentController = parentController;

        if (admin != null) {this.currentAdmin = admin;}

        usernameLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
        statusLabel.setText(user.getStatus().toString());

        if (user.getProfilePicture() != null) {
            profileImage.setImage(new Image(new ByteArrayInputStream(user.getProfilePicture())));
        }

        if (user.getStatus() == UserStatus.BANNED) {
            banButton.setDisable(true);
            activateButton.setDisable(false);
        } else {
            banButton.setDisable(false);
            activateButton.setDisable(true);
        }
    }

    @FXML
    private void handleDeleteUser() {
        if (user != null) {
            if(AdminActionLogger.showPopUpWind(currentAdmin.getId(), "DELETE_USER", user.getId(), "USER")) {
                userDAO.deleteUser(user.getId());
                parentController.refreshUserList(); // refresh the UI
            }
        }
    }

    @FXML
    private void handleBanUser() {
       if(AdminActionLogger.showPopUpWind(currentAdmin.getId(), "BAN_USER", user.getId(), "USER")) {
           userDAO.updateUserStatus(user.getId(), UserStatus.BANNED);
           statusLabel.setText("BANNED");
           banButton.setDisable(true);
           activateButton.setDisable(false);
       }
    }

    @FXML
    private void handleActivateUser() {
        AdminActionLogger.logAdminAction(currentAdmin.getId(), "USER_REACTIVATION", user.getId(), "USER","REACTIVATED");
        userDAO.updateUserStatus(user.getId(), UserStatus.ACTIVE);
        statusLabel.setText("ACTIVE");
        banButton.setDisable(false);
        activateButton.setDisable(true);
    }
}
