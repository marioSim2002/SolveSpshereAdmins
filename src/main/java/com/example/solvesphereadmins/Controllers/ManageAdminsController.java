package com.example.solvesphereadmins.Controllers;

import DatabaseUnit.AdminDAO;
import DatabaseUnit.AdminDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;

import com.example.solvesphereadmins.AdminUnit.Admin;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ManageAdminsController {
    @FXML private TableView<Admin> adminTable;
    @FXML private TableColumn<Admin, Integer> idColumn;
    @FXML private TableColumn<Admin, String> usernameColumn;
    @FXML private TableColumn<Admin, String> emailColumn;
    @FXML private TableColumn<Admin, String> statusColumn;
    @FXML private TableColumn<Admin, String> roleColumn;

    private final AdminDAO adminDAO = new AdminDAOImpl();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadAdminData();
    }

    private void loadAdminData() {
        adminTable.getItems().setAll(adminDAO.getAllAdmins());
    }

    @FXML
    private void handleAddAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/AddAdmin.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Admin");
            stage.setWidth(300);
            stage.setHeight(450);
            stage.setResizable(false);
            stage.showAndWait();
            loadAdminData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleSuspendAdmin() {
        Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
        if (selectedAdmin == null) {
            showAlert("No Admin Selected", "Please select an admin to suspend.");
            return;
        }

        if ("SUSPENDED".equals(selectedAdmin.getStatus())) {
            showAlert("Already Suspended", "This admin is already suspended.");
            return;
        }

        Optional<ButtonType> confirmation = showConfirmation("Suspend Admin", "Are you sure you want to suspend " + selectedAdmin.getUsername() + "?");
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            adminDAO.updateAdminStatus(selectedAdmin.getId(), "SUSPENDED");
            loadAdminData(); // Refresh table
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }
}
