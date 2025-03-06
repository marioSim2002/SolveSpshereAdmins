package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.Reports.ReportDAO;
import com.example.solvesphereadmins.RetrievedUserData.Problem;
import com.example.solvesphereadmins.RetrievedUserData.ProblemDAO;
import com.example.solvesphereadmins.RetrievedUserData.ProblemDAOImpl;
import com.example.solvesphereadmins.RetrievedUserData.ReportDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ProblemItemController {
    @FXML private Label reportsStat;
    @FXML private Label titleLabel;
    @FXML private Label categoryLabel;
    @FXML private Label createdAtLabel;
    @FXML private Button deleteButton;

    private Problem problem;
    private ManageProblemsController parentController;
    private final ProblemDAO problemDAO = new ProblemDAOImpl();
    private final ReportDAO reportDAO = new ReportDAOImpl();
    public void setProblem(Problem problem, ManageProblemsController parentController) {
        this.problem = problem;
        this.parentController = parentController;

        titleLabel.setText(problem.getTitle());
        categoryLabel.setText("Category: " + problem.getCategory());
        createdAtLabel.setText("Created At: " + problem.getCreatedAt().toString());

        if(reportDAO.getReportsByProblemId(problem.getId()).size()>40){
            reportsStat.setVisible(true);
            reportsStat.setText("HIGH REPORTS NUMBER");
        }
    }

    @FXML
    private void handleDeleteProblem() {
        if (problem != null) {
            problemDAO.deleteProblem(problem.getId());
            showAlert("Problem Deleted", "The post has been removed.");
            parentController.refreshPostList();
        }
    }

    @FXML
    private void openProblemDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/ProblemDetails.fxml"));
            AnchorPane pane = loader.load();

            ProblemDetailsController controller = loader.getController();
            controller.setProblem(problem);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Problem Details");
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
