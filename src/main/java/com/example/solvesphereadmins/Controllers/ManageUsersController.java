package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.RetrievedUserData.User;
import com.example.solvesphereadmins.RetrievedUserData.User.UserStatus;
import com.example.solvesphereadmins.RetrievedUserData.UserDAO;
import com.example.solvesphereadmins.RetrievedUserData.UserDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ManageUsersController {

    @FXML private ComboBox<String> filterRoleComboBox; // ✅ Fix: Add String parameter
    @FXML private ListView<HBox> userListView;
    @FXML private TextField searchField;

    private final UserDAO userDAO = new UserDAOImpl();
    private ObservableList<User> allUsers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadUserData();

        // ✅ Fix: Properly set ComboBox values
        filterRoleComboBox.setItems(FXCollections.observableArrayList("All", "USER", "ACTIVE", "BANNED"));
        filterRoleComboBox.setValue("All");

        // Detect click events on list items
        userListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click to open user details
                int selectedIndex = userListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    openUserDetails(allUsers.get(selectedIndex)); //pass the clicked user
                }
            }
        });
    }

    private void loadUserData() {
        List<User> users = userDAO.getAllUsers();
        allUsers.setAll(users);
        updateUserList(users);
    }

    private void updateUserList(List<User> users) {
        ObservableList<HBox> userItems = FXCollections.observableArrayList();
        for (User user : users) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/UserItem.fxml"));
                HBox userItem = loader.load();

                UserItemController controller = loader.getController();
                controller.setUser(user);

                userItems.add(userItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userListView.setItems(userItems);
    }

    private void openUserDetails(User user) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/UserDetails.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("User Details");

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            UserDetailsController controller = loader.getController();
            controller.setUser(user);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearch() {
        String query = searchField.getText().toLowerCase();
        List<User> filteredUsers = allUsers.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(query) || u.getEmail().toLowerCase().contains(query))
                .collect(Collectors.toList());
        updateUserList(filteredUsers);
    }

    @FXML
    public void handleFilter(ActionEvent actionEvent) {
        String selectedRole = filterRoleComboBox.getValue();
        if ("All".equals(selectedRole)) {
            updateUserList(allUsers);
        } else {
            List<User> filteredUsers = allUsers.stream()
                    .filter(u -> u.getStatus().toString().equals(selectedRole))
                    .collect(Collectors.toList());
            updateUserList(filteredUsers);
        }
    }
}