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
    @FXML private PieChart categoryChart;

    private final ProblemDAO problemDAO = new ProblemDAOImpl();
    private ObservableList<Problem> allProblems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadProblems();
    }

    private void loadProblems() {
        List<Problem> problems = problemDAO.getAllProblems();
        allProblems.setAll(problems);
        updatePostsList(problems);
        updateCategoryChart(problems);
    }

    private void updatePostsList(List<Problem> problems) {
        ObservableList<HBox> postItems = FXCollections.observableArrayList();
        for (Problem problem : problems) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/ProblemItem.fxml"));
                HBox problemItem = loader.load();

                // Get the controller for the item
                ProblemItemController controller = loader.getController();
                controller.setProblem(problem, this);

                postItems.add(problemItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        postsListView.setItems(postItems);
    }

    private void updateCategoryChart(List<Problem> problems) {
        Map<String, Long> categoryCount = problems.stream()
                .collect(Collectors.groupingBy(Problem::getCategory, Collectors.counting()));

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        categoryCount.forEach((category, count) -> chartData.add(new PieChart.Data(category, count)));

        categoryChart.setData(chartData);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase().trim();
        List<Problem> filtered = allProblems.stream()
                .filter(p -> p.getTitle().toLowerCase().contains(searchText) || String.valueOf(p.getUserId()).contains(searchText) ||
                        p.getCategory().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
        updatePostsList(filtered);
    }

    public void refreshPostList() {
        loadProblems();
    }
}
