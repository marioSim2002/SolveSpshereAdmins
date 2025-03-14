package com.example.solvesphereadmins.Controllers;
import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.AdminUnit.SessionManager;
import com.example.solvesphereadmins.RetrievedUserData.Comment;
import com.example.solvesphereadmins.RetrievedUserData.CommentDAO;
import com.example.solvesphereadmins.RetrievedUserData.CommentDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManageCommentsController {
    @FXML private ListView<HBox> commentsListView;
    @FXML private TextField searchField;
    @FXML private Label totalCommentsLabel;
    @FXML private ComboBox<String> sortComboBox;
    @FXML private CheckBox solutionFilterCheckBox;
    private Admin currentAdmin;

    private final CommentDAO commentDAO = new CommentDAOImpl();
    private ObservableList<Comment> allComments = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadComments();
        sortComboBox.setItems(FXCollections.observableArrayList("Most Upvoted", "Least Upvoted"));
        sortComboBox.setValue("Most Upvoted"); // default
        // even listener for sorting
        sortComboBox.setOnAction(event -> handleSort());
    }

    private void loadComments() {
        List<Comment> comments = commentDAO.getAllComments();
        allComments.setAll(comments);
        updateCommentList(comments);
    }

    private void updateCommentList(List<Comment> comments) {
        ObservableList<HBox> commentItems = FXCollections.observableArrayList();
        for (Comment comment : comments) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/CommentItem.fxml"));
                HBox commentItem = loader.load();

                CommentItemController controller = loader.getController();
                controller.setComment(comment, this);

                commentItems.add(commentItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        commentsListView.setItems(commentItems);
        totalCommentsLabel.setText("Total Comments: " + comments.size());
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase();
        List<Comment> filteredComments = allComments.stream()
                .filter(c -> c.getContent().toLowerCase().contains(query) ||
                        String.valueOf(c.getUserId()).contains(query))
                .collect(Collectors.toList());
        updateCommentList(filteredComments);
    }

    public void refreshCommentList() {loadComments();}


    @FXML
    private void handleSort() {
        String selectedOption = sortComboBox.getValue();
        if (selectedOption != null) {
            List<Comment> sortedList = new ArrayList<>(allComments);
            sortedList.sort((c1, c2) -> {
                if ("Most Upvoted".equals(selectedOption)) {
                    return Integer.compare(c2.getUpvotes(), c1.getUpvotes()); // Descending
                } else {
                    return Integer.compare(c1.getUpvotes(), c2.getUpvotes()); // Ascending
                }
            });
            updateCommentsList(sortedList);
        }
    }

    @FXML
    private void handleFilterSolutions() {
        boolean filterSolutions = solutionFilterCheckBox.isSelected();
        List<Comment> filteredList = allComments.stream()
                .filter(comment -> !filterSolutions || comment.isSolution()) //checked, filter solutions
                .collect(Collectors.toList());
        updateCommentsList(filteredList);
    }


    private void updateCommentsList(List<Comment> comments) {
        ObservableList<HBox> commentItems = FXCollections.observableArrayList();

        for (Comment comment : comments) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/CommentItem.fxml"));
                HBox commentItem = loader.load();

                CommentItemController controller = loader.getController();
                controller.setComment(comment, this); // set the comment and pass the controller

                commentItems.add(commentItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        commentsListView.setItems(commentItems);
    }

    @FXML
    private void handleClose() {
        ((Stage) totalCommentsLabel.getScene().getWindow()).close();
    }
}
