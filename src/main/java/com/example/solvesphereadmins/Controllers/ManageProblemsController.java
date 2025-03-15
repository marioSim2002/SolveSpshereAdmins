package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.RetrievedUserData.Problem;
import com.example.solvesphereadmins.RetrievedUserData.ProblemDAO;
import com.example.solvesphereadmins.RetrievedUserData.ProblemDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManageProblemsController {
    @FXML private TextField searchField;
    @FXML private ListView<HBox> postsListView;
    @FXML private ListView<HBox> adminPostsListView; // admin problems section
    @FXML private PieChart categoryChart; //pie chart for user problem categories
    @FXML private PieChart adminCategoryChart; // pie chart for admin problem categories

    private final ProblemDAO problemDAO = new ProblemDAOImpl();
    private ObservableList<Problem> allProblems = FXCollections.observableArrayList();
    private ObservableList<Problem> adminProblems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadProblems();
        loadAdminProblems();
    }

    private void loadProblems() {
        List<Problem> problems = problemDAO.getAllProblems();
        allProblems.setAll(problems);
        updatePostsList(problems);
        updateCategoryChart(problems);
    }

    public void loadAdminProblems() {
        List<Problem> adminProbs = problemDAO.getAdminProblems();
        adminProblems.setAll(adminProbs);
        updateAdminPostsList(adminProbs);
        updateAdminCategoryChart(adminProbs);
    }

    private void updatePostsList(List<Problem> problems) {
        ObservableList<HBox> postItems = FXCollections.observableArrayList();
        for (Problem problem : problems) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/ProblemItem.fxml"));
                HBox problemItem = loader.load();

                ProblemItemController controller = loader.getController();
                controller.setProblem(problem, this);

                postItems.add(problemItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (postsListView != null) { postsListView.setItems(postItems); }
    }

    private void updateAdminPostsList(List<Problem> adminProblems) {
        ObservableList<HBox> adminPostItems = FXCollections.observableArrayList();
        for (Problem problem : adminProblems) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/ProblemItem.fxml"));
                HBox problemItem = loader.load();

                ProblemItemController controller = loader.getController();
                controller.setProblem(problem,this);

                adminPostItems.add(problemItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (adminPostsListView != null) { adminPostsListView.setItems(adminPostItems); }
    }

    private void updateCategoryChart(List<Problem> problems) {
        Map<String, Long> categoryCount = problems.stream()
                .collect(Collectors.groupingBy(Problem::getCategory, Collectors.counting()));

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        categoryCount.forEach((category, count) -> chartData.add(new PieChart.Data(category, count)));
        categoryChart.setData(chartData);
    }


    private void updateAdminCategoryChart(List<Problem> adminProblems) {
        Map<String, Long> adminCategoryCount = adminProblems.stream()
                .collect(Collectors.groupingBy(Problem::getCategory, Collectors.counting()));

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        adminCategoryCount.forEach((category, count) -> chartData.add(new PieChart.Data(category, count)));

      adminCategoryChart.setData(chartData);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase().trim();
        List<Problem> filteredUserProblems = allProblems.stream()
                .filter(p -> p.getTitle().toLowerCase().contains(searchText) ||
                        String.valueOf(p.getUserId()).contains(searchText) ||
                        p.getCategory().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        List<Problem> filteredAdminProblems = adminProblems.stream()
                .filter(p -> p.getTitle().toLowerCase().contains(searchText) ||
                        String.valueOf(p.getUserId()).contains(searchText) ||
                        p.getCategory().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        updatePostsList(filteredUserProblems);
        updateAdminPostsList(filteredAdminProblems);
    }

    public void refreshPostList() {
        loadProblems();
        loadAdminProblems();
    }
}
