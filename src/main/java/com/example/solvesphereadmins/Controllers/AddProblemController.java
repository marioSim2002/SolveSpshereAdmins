package com.example.solvesphereadmins.Controllers;
import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.AlertsUnit;
import com.example.solvesphereadmins.RetrievedUserData.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class AddProblemController {
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField tagsField;
    @FXML
    private CheckBox ageRestrictionCheckbox;
    @FXML
    private VBox similarProblemsListView; // will hold ProblemItem components
    private Admin currentAdmin;

    private final ProblemDAO problemDAO = new ProblemDAOImpl();

    @FXML
    public void initialize() {

        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchSimilarProblems(newValue, descriptionField.getText());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        descriptionField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchSimilarProblems(titleField.getText(), newValue);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setAdmin(Admin admin){
        this.currentAdmin = admin;
    }

    /**
     * Fetch and display similar problems dynamically based on title and description.
     */
    private void searchSimilarProblems(String titleInput, String descInput) throws SQLException, ClassNotFoundException {
        titleInput = titleInput.trim();
        descInput = descInput.trim();

        //at least one meaningful input exists
        if (titleInput.isEmpty() && descInput.isEmpty()) {
            similarProblemsListView.getChildren().clear();
            return;
        }

        List<Problem> similarProblems = problemDAO.findSimilarProblemsByTitleAndDescription(titleInput, descInput);

        Platform.runLater(() -> {
            similarProblemsListView.getChildren().clear();
            for (Problem problem : similarProblems) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/ProblemItem.fxml"));
                    Node problemItem = loader.load();
                    ProblemItemController controller = loader.getController();
                    controller.setProblem(problem,new ManageProblemsController());
                    similarProblemsListView.getChildren().add(problemItem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    public int getProblemCommentCount(Problem problem) {
//        CommentDAO commentDAO = new CommentDAOImpl();
//        return commentDAO.getCommentCountByProblemId(problem.getId());
//    }

    @FXML
    private void submitProblem() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String category = categoryField.getText();
        String tagsText = tagsField.getText();


        boolean isAgeRestricted = ageRestrictionCheckbox.isSelected();

        //// todo - create an add - only admin problem solutions table
        System.out.println(currentAdmin.getId());
        Problem problem = new Problem(0, 0, title,description, category, Timestamp.valueOf(LocalDateTime.now()) , isAgeRestricted);
        boolean isSuccess = problemDAO.addProblem(problem);

        if (isSuccess) {
            AlertsUnit.successAddAlert();
            clearFields();
        } else {
            AlertsUnit.showErrorAlert("An error occurred.");
        }
    }

    private void clearFields() {
        titleField.clear();
        descriptionField.clear();
        categoryField.clear();
        tagsField.clear();
        ageRestrictionCheckbox.setSelected(false);
        similarProblemsListView.getChildren().clear();
    }
}
