package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.FileExporter;
import com.example.solvesphereadmins.RetrievedUserData.Problem;
import com.example.solvesphereadmins.RetrievedUserData.ProblemDAO;
import com.example.solvesphereadmins.RetrievedUserData.ProblemDAOImpl;
import com.example.solvesphereadmins.RetrievedUserData.User;
import com.example.solvesphereadmins.RetrievedUserData.UserDAO;
import com.example.solvesphereadmins.RetrievedUserData.UserDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PlatformActivityController {
    @FXML private LineChart<String, Number> problemsChart;
    @FXML private LineChart<String, Number> usersChart;

    private final ProblemDAO problemDAO = new ProblemDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    @FXML
    public void initialize() {
        loadProblemActivity();
        loadUserActivity();
    }

    private void loadProblemActivity() {
        List<Problem> problems = problemDAO.getAllProblems();
        Map<String, Long> problemDateCounts = countByDate(problems.stream().map(Problem::getCreatedAt).collect(Collectors.toList()));

        XYChart.Series<String, Number> problemSeries = new XYChart.Series<>();
        problemSeries.setName("Problems Posted");

        for (Map.Entry<String, Long> entry : problemDateCounts.entrySet()) {
            problemSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        problemsChart.getData().clear();
        problemsChart.getData().add(problemSeries);
    }

    private void loadUserActivity() {
        List<User> users = userDAO.getAllUsers();
        Map<String, Long> userDateCounts = countByDate(users.stream()
                .map(user -> Timestamp.valueOf(user.getRegistrationDate() + " 00:00:00")) // convert string to Timestamp
                .collect(Collectors.toList()));

        XYChart.Series<String, Number> userSeries = new XYChart.Series<>();
        userSeries.setName("Users Joined");

        for (Map.Entry<String, Long> entry : userDateCounts.entrySet()) {
            userSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        usersChart.getData().clear();
        usersChart.getData().add(userSeries);
    }

    private Map<String, Long> countByDate(List<Timestamp> timestamps) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return timestamps.stream()
                .map(dateFormat::format)
                .collect(Collectors.groupingBy(date -> date, TreeMap::new, Collectors.counting()));
    }

    @FXML
    private void handleClose() {
        ((Stage) problemsChart.getScene().getWindow()).close();
    }
}
