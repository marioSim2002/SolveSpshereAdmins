package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.Reports.ReportDAO;
import com.example.solvesphereadmins.Reports.ReportDAOImpl;
import com.example.solvesphereadmins.RetrievedUserData.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class ProblemDetailsController {
    @FXML private Label ownerId;
    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label categoryLabel;
    @FXML private Label createdAtLabel;
    @FXML private TableView<Report> reportsTable;
    @FXML private TableColumn<Report, Long> reporterIdColumn;
    @FXML private TableColumn<Report, String> reportReasonColumn;
    @FXML private TableColumn<Report, String> reportCreatedAtColumn;

    private ManageProblemsController parentController;
    private final ReportDAO reportDAO = new ReportDAOImpl();
    private Problem problem;
    private Timeline refreshTimeline; // auto-refresh data

    private void startAutoRefresh() {
        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> refreshReportData()));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    private void refreshReportData() {
        Platform.runLater(this::loadProblemReports);
    }

    public void setProblem(Problem problem) {
        this.problem = problem;

        titleLabel.setText(problem.getTitle());
        descriptionLabel.setText("Description: " + problem.getDescription());
        categoryLabel.setText("Category: " + problem.getCategory());
        createdAtLabel.setText("Created At: " + problem.getCreatedAt().toString());
        ownerId.setText("Post owner ID: " + problem.getUserId());

        setupReportTable();
        loadProblemReports();
        startAutoRefresh();
    }

    private void setupReportTable() {
        reporterIdColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getReporterId()));
        reportReasonColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getReportReason()));
        reportCreatedAtColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
    }

    private void loadProblemReports() {
        List<Report> reports = reportDAO.getReportsByProblemId(problem.getId());
        ObservableList<Report> reportList = FXCollections.observableArrayList(reports);
        reportsTable.setItems(reportList);
    }

    @FXML
    private void handleClose() {
        ((Stage) titleLabel.getScene().getWindow()).close();
    }

    @FXML
    private void handleDelete() {
        if (problem != null) {
            ProblemDAO problemDAO = new ProblemDAOImpl();
            problemDAO.deleteProblem(problem.getId());
            parentController.refreshPostList();
        }
    }

    @FXML
    private void handleDeleteReport() {
        Report selectedReport = reportsTable.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Report");
            alert.setHeaderText("Are you sure you want to delete this report?");
            alert.setContentText("This action cannot be undone.");

            if (alert.showAndWait().get() == ButtonType.OK) {
                reportDAO.deleteReport(selectedReport.getId()); // delete from DB
                loadProblemReports(); //refresh table after deletion
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Report Selected");
            alert.setContentText("Please select a report to delete.");
            alert.showAndWait();
        }
    }

}
