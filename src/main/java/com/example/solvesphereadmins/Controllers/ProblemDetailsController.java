package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.Reports.ReportDAO;
import com.example.solvesphereadmins.RetrievedUserData.*;
import com.example.solvesphereadmins.RetrievedUserData.ReportDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;


public class ProblemDetailsController {
    @FXML private Label ownerId;
    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label categoryLabel;
    @FXML private Label createdAtLabel;
    @FXML private TableView<Report> reportsTable;
    @FXML private TableColumn<Report, Integer> reporterIdColumn;
    @FXML private TableColumn<Report, String> reportReasonColumn;
    @FXML private TableColumn<Report, String> reportCreatedAtColumn;

    private ManageProblemsController parentController;
    private final ReportDAO reportDAO = new ReportDAOImpl();
    private Problem problem;

    public void setProblem(Problem problem) {
        this.problem = problem;

        titleLabel.setText(problem.getTitle());
        descriptionLabel.setText("Description: " + problem.getDescription());
        categoryLabel.setText("Category: " + problem.getCategory());
        createdAtLabel.setText("Created At: " + problem.getCreatedAt().toString());
        ownerId.setText("Post owner ID: "+ problem.getUserId());
        loadProblemReports();
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

}
