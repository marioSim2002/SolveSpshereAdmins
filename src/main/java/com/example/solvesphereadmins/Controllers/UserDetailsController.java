package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.AdminUnit.AdminAction;
import com.example.solvesphereadmins.AdminUnit.AdminActionLogger;
import com.example.solvesphereadmins.RetrievedUserData.Comment;
import com.example.solvesphereadmins.RetrievedUserData.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDetailsController {
    @FXML private Label idLabel;
    @FXML private ImageView profileImage;
    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;
    @FXML private Label statusLabel;
    @FXML private Label countryLabel;
    @FXML private Label dobLabel;
    @FXML private TableView<Comment> commentsTable;
    @FXML private TableColumn<Comment, Long> commentIdColumn;
    @FXML private TableColumn<Comment, String> commentContentColumn;
    @FXML private TableColumn<Comment, String> commentCreatedAtColumn;
    @FXML private TableView<Problem> problemsTable;
    @FXML private TableColumn<Problem, Long> problemIdColumn;
    @FXML private TableColumn<Problem, String> problemTitleColumn;
    @FXML private TableColumn<Problem, String> problemCategoryColumn;
    @FXML private TableColumn<Problem, String> problemCreatedAtColumn;
    @FXML private PieChart categoryChart;
    private final CommentDAO commentDAO = new CommentDAOImpl();
    private final ProblemDAO problemDAO = new ProblemDAOImpl();
    private User user;
    private Admin admin ;
    private Timeline refreshTimeline; //auto-refresh data

    private void startAutoRefresh() {
        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> refreshUserData()));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }
    private void refreshUserData() {
        Platform.runLater(() -> {
            loadUserComments();
            loadUserProblems();
            loadCategoryChart();
        });
    }

    public void setUser(User user) {
        this.user = user;

        usernameLabel.setText("Username: " + user.getUsername());
        emailLabel.setText("Email: " + user.getEmail());
        statusLabel.setText("Status: " + user.getStatus().toString());
        countryLabel.setText("Country: " + user.getCountry());
        dobLabel.setText("Date of Birth: " + user.getDateOfBirth());
        idLabel.setText("User ID: "+ user.getId());

        //convert profile picture BLOB to image
        if (user.getProfilePicture() != null && user.getProfilePicture().length > 0) {
            profileImage.setImage(new Image(new ByteArrayInputStream(user.getProfilePicture())));
        }
        //load user data
        setupCommentTable();
        setupProblemTable();
        loadUserComments();
        loadUserProblems();
        loadCategoryChart();
        startAutoRefresh();
    }

    private void setupCommentTable() {
        commentIdColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        commentContentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getContent()));
        commentCreatedAtColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
    }

    private void setupProblemTable() {
        problemIdColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        problemTitleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitle()));
        problemCategoryColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategory()));
        problemCreatedAtColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
    }

    private void loadUserComments() {
        List<Comment> comments = commentDAO.getCommentsByUserId(user.getId());
        ObservableList<Comment> commentList = FXCollections.observableArrayList(comments);
        commentsTable.setItems(commentList);
    }

    private void loadUserProblems() {
        List<Problem> problems = problemDAO.getProblemsByUserId(user.getId());
        ObservableList<Problem> problemList = FXCollections.observableArrayList(problems);
        problemsTable.setItems(problemList);
    }

    private void loadCategoryChart() {
        List<Problem> problems = problemDAO.getProblemsByUserId(user.getId());

        // Group problems by category
        Map<String, Long> categoryCount = problems.stream()
                .collect(Collectors.groupingBy(Problem::getCategory, Collectors.counting()));

        // Populate chart
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        categoryCount.forEach((category, count) -> chartData.add(new PieChart.Data(category, count)));

        categoryChart.setData(chartData);
    }

    @FXML
    private void handleDeleteComment() {
        Comment selectedComment = commentsTable.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            if (AdminActionLogger.showPopUpWind(admin.getId(), "COMMENT_DELETION", selectedComment.getId(), "COMMENT")) {
                commentDAO.deleteComment(selectedComment.getId());
                loadUserComments();
            }
        }
    }

    @FXML
    private void handleDeleteProblem() {
        Problem selectedProblem = problemsTable.getSelectionModel().getSelectedItem();
        if (selectedProblem != null) {
            AdminActionLogger.showPopUpWind(admin.getId(), "DELETE_PROBLEM", selectedProblem.getId(), "PROBLEM");
            problemDAO.deleteProblem(selectedProblem.getId());
            loadUserProblems(); // refresh table
            loadCategoryChart(); // refresh chart
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) usernameLabel.getScene().getWindow();
        stage.close();
    }

    public void setAdmin(Admin currentAdmin) {
        this.admin = currentAdmin;
    }
}