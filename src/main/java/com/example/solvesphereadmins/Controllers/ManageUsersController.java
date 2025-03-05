package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.RetrievedUserData.User;
import com.example.solvesphereadmins.RetrievedUserData.User.UserStatus;
import com.example.solvesphereadmins.RetrievedUserData.UserDAO;
import com.example.solvesphereadmins.RetrievedUserData.UserDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ManageUsersController {
    @FXML private ListView<HBox> userListView;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterRoleComboBox;

    private final UserDAO userDAO = new UserDAOImpl();
    private ObservableList<User> allUsers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadUserData();
        filterRoleComboBox.setItems(FXCollections.observableArrayList("All", "ACTIVE", "INACTIVE", "BANNED"));
        filterRoleComboBox.setValue("All");
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


    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase();
        List<User> filteredUsers = allUsers.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(query) || u.getEmail().toLowerCase().contains(query))
                .collect(Collectors.toList());
        updateUserList(filteredUsers);
    }

    @FXML
    private void handleFilter() {
        String selectedStatus = filterRoleComboBox.getValue();
        if ("All".equals(selectedStatus)) {
            updateUserList(allUsers);
        } else {
            UserStatus status = UserStatus.valueOf(selectedStatus);
            List<User> filteredUsers = allUsers.stream()
                    .filter(u -> u.getStatus() == status)
                    .collect(Collectors.toList());
            updateUserList(filteredUsers);
        }
    }
}
