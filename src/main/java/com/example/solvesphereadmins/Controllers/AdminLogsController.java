package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.AdminUnit.AdminAction;
import com.example.solvesphereadmins.AdminUnit.AdminActionDAO;
import com.example.solvesphereadmins.AdminUnit.AdminActionDAOImpl;
import com.example.solvesphereadmins.FileExporter;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class AdminLogsController {
    @FXML private TableView<AdminAction> logsTable;
    @FXML private TableColumn<AdminAction, Long> adminIdColumn;
    @FXML private TableColumn<AdminAction, String> actionTypeColumn;
    @FXML private TableColumn<AdminAction, Long> targetIdColumn;
    @FXML private TableColumn<AdminAction, String> targetTypeColumn;
    @FXML private TableColumn<AdminAction, String> descriptionColumn;
    @FXML private TableColumn<AdminAction, String> timestampColumn;

    private final AdminActionDAO adminActionDAO = new AdminActionDAOImpl();

    @FXML
    public void initialize() {
        setupTable();
        loadLogs();
    }

    private void setupTable() {
        adminIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAdminId()));
        actionTypeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getActionType()));
        targetIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTargetId()));
        targetTypeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTargetType()));
        descriptionColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));
        timestampColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTimestamp().toString()));
    }

    private void loadLogs() {
        List<AdminAction> logs = adminActionDAO.getAllAdminActions();
        ObservableList<AdminAction> logList = FXCollections.observableArrayList(logs);
        logsTable.setItems(logList);
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) logsTable.getScene().getWindow();
        stage.close();
    }

    public void handlePDFExportation() {FileExporter.exportPdfFile();}
}
