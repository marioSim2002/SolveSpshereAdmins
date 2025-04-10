package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.AdminUnit.Admin;
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
    @FXML private ComboBox<String> sortComboBox;
    @FXML private ComboBox<String> filterRoleComboBox;
    @FXML private ListView<HBox> userListView;
    @FXML private TextField searchField;

    private final UserDAO userDAO = new UserDAOImpl();
    private ObservableList<User> allUsers = FXCollections.observableArrayList();
    private Admin currentAdmin;

    @FXML
    public void initialize() {
        loadUserData();  
        filterRoleComboBox.setItems(FXCollections.observableArrayList("All", "ACTIVE", "BANNED"));
        filterRoleComboBox.setValue("All");

        sortComboBox.setItems(FXCollections.observableArrayList("Default", "Newest First", "Oldest First"));
        sortComboBox.setValue("Default");

        sortComboBox.setOnAction(event -> handleSort());

        //detect click events on list items
        userListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int selectedIndex = userListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    openUserDetails(allUsers.get(selectedIndex)); //pass the clicked user
                }
            }
        });
    }

    public void setAdmin(Admin admin) {this.currentAdmin = admin;}


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
                controller.setUser(user,this,currentAdmin);

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
            stage.setHeight(600);
            stage.setTitle("User Details");
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            UserDetailsController controller = loader.getController();
            controller.setUser(user);
            controller.setAdmin(currentAdmin);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearch() {
        String query = searchField.getText().toLowerCase();
        List<User> filteredUsers = allUsers.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(query) || u.getEmail().toLowerCase().contains(query)||String.valueOf(u.getId()).contains(query))
                .collect(Collectors.toList());
        updateUserList(filteredUsers);
    }

    @FXML
    public void handleFilter() {
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
    public void refreshUserList() {
        List<User> users = userDAO.getAllUsers();
        updateUserList(users);
    }

    @FXML
    public void handleSort() {
        String selectedSort = sortComboBox.getValue();
        List<User> sortedUsers = allUsers.stream().sorted((u1, u2) -> {
            if ("Newest First".equals(selectedSort)) {
                return u2.getRegistrationDate().compareTo(u1.getRegistrationDate()); //descending
            } else if ("Oldest First".equals(selectedSort)) {
                return u1.getRegistrationDate().compareTo(u2.getRegistrationDate()); //ascending
            }
            return 0; //def
        }).collect(Collectors.toList());

        updateUserList(sortedUsers);
    }

}