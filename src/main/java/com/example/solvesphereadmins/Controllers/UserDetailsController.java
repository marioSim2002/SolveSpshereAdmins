package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.RetrievedUserData.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.ByteArrayInputStream;

public class UserDetailsController {
    @FXML private ImageView profileImage;
    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;
    @FXML private Label statusLabel;
    @FXML private Label countryLabel;
    @FXML private Label dobLabel;
    @FXML private Button closeButton;

    private User user;

    public void setUser(User user) {
        this.user = user;

        usernameLabel.setText("Username: " + user.getUsername());
        emailLabel.setText("Email: " + user.getEmail());
        statusLabel.setText("Status: " + user.getStatus().toString());
        countryLabel.setText("Country: " + user.getCountry());
        dobLabel.setText("Date of Birth: " + user.getDateOfBirth());

        if (user.getProfilePicture() != null) {
            profileImage.setImage(new Image(new ByteArrayInputStream(user.getProfilePicture())));
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
