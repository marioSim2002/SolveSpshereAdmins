package com.example.solvesphereadmins.Controllers;

import DatabaseUnit.AdminDAO;
import DatabaseUnit.AdminDAOImpl;
import com.example.solvesphereadmins.AdminUnit.AdminActionLogger;
import com.example.solvesphereadmins.AdminUnit.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import com.example.solvesphereadmins.AdminUnit.Admin;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManageAdminsController {
    @FXML private TableView<Admin> adminTable;
    @FXML private TableColumn<Admin, Integer> idColumn;
    @FXML private TableColumn<Admin, String> usernameColumn;
    @FXML private TableColumn<Admin, String> emailColumn;
    @FXML private TableColumn<Admin, String> statusColumn;
    @FXML private TableColumn<Admin, String> roleColumn;

    @FXML private Label superAdminCount;
    @FXML private Label adminCount;
    @FXML private ComboBox<String> filterRoleComboBox;

    private final AdminDAO adminDAO = new AdminDAOImpl();
    private final ObservableList<Admin> allAdmins = FXCollections.observableArrayList(); //all admins

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        adminTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadAdminData();
        filterRoleComboBox.setValue("All"); //default to "All"

    }

    private void loadAdminData() {
        List<Admin> admins = adminDAO.getAllAdmins();
        allAdmins.setAll(admins);
        adminTable.setItems(allAdmins);

        updateAdminCounts();
    }

    private void updateAdminCounts() {
        long superAdminTotal = allAdmins.stream().filter(a -> "SUPER_ADMIN".equals(a.getRole())).count();
        long adminTotal = allAdmins.stream().filter(a -> "ADMIN".equals(a.getRole())).count();

        superAdminCount.setText(String.valueOf(superAdminTotal));
        adminCount.setText(String.valueOf(adminTotal));
    }
    @FXML
    private void handleAddAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/AddAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Admin");
            stage.setWidth(350);
            stage.setHeight(450);
            stage.setResizable(false);
            stage.showAndWait();
            loadAdminData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //todo - option to delete admins //

    @FXML
    private void handleFilter() {
        String selectedRole = filterRoleComboBox.getValue();
        if ("All".equals(selectedRole)) {
            adminTable.setItems(allAdmins); //show all
        } else {  /// filter according to selected keyword - lambda
            ObservableList<Admin> filteredList = FXCollections.observableArrayList(
                    allAdmins.stream().filter(a -> a.getRole().equals(selectedRole)).collect(Collectors.toList())
            );
            adminTable.setItems(filteredList);
        }
    }


    @FXML
    private void handleSuspendAdmin() {
        Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
        long selectedAdminId = (long) selectedAdmin.getId();
        if(selectedAdminIsCurrnetUser(selectedAdminId)){return;}
        if ("SUSPENDED".equals(selectedAdmin.getStatus())) {
            showAlert("Already Suspended", "This admin is already suspended.");
            return;
        }

        Optional<ButtonType> confirmation = showConfirmation("Suspend Admin", "Are you sure you want to suspend " + selectedAdmin.getUsername() + "?");
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            AdminActionLogger.showPopUpWind(SessionManager.getCurrentAdmin().getId(),"SUSPEND_ADMIN",selectedAdminId,"ADMIN");
            adminDAO.updateAdminStatus(selectedAdmin.getId(), "SUSPENDED");
            loadAdminData(); // refresh table
        }
    }


    @FXML
    public void handleActivateAdmin() {
        Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
        long selectedAdminId = (long) selectedAdmin.getId();
        if(selectedAdminIsCurrnetUser(selectedAdminId)){return;}//check if admin trying to perform act to himself

        if ("SUSPENDED".equals(selectedAdmin.getStatus())) {
            Optional<ButtonType> confirmation = showConfirmation("Re-Activate Admin", "Are you sure you want to Activate " + selectedAdmin.getUsername() + "?");
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                AdminActionLogger.showPopUpWind(SessionManager.getCurrentAdmin().getId(), "ADMIN_ACTIVATION", selectedAdminId, "ADMIN");
                adminDAO.updateAdminStatus(selectedAdmin.getId(), "ACTIVE");
                loadAdminData(); // refresh table
            }
        }
        else {
            showAlert("Already Active", "This admin is already active.");
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

    private boolean selectedAdminIsCurrnetUser(long selecteAdminId){
        return SessionManager.getCurrentAdmin().getId() == selecteAdminId;
    }

}
