package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.RetrievedUserData.Problem;
import com.example.solvesphereadmins.RetrievedUserData.ProblemDAO;
import com.example.solvesphereadmins.RetrievedUserData.ProblemDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProblemItemController {
    @FXML private Label titleLabel;
    @FXML private Label categoryLabel;
    @FXML private Label createdAtLabel;
    @FXML private Button deleteButton;

    private Problem problem;
    private ManageProblemsController parentController;
    private final ProblemDAO problemDAO = new ProblemDAOImpl();

    public void setProblem(Problem problem, ManageProblemsController parentController) {
        this.problem = problem;
        this.parentController = parentController;

        titleLabel.setText(problem.getTitle());
        categoryLabel.setText("Category: " + problem.getCategory());
        createdAtLabel.setText("Created At: " + problem.getCreatedAt().toString());
    }

    @FXML
    private void handleDeleteProblem() {
        if (problem != null) {
            problemDAO.deleteProblem(problem.getId());
            showAlert("Problem Deleted", "The post has been removed.");
            parentController.refreshPostList();
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
