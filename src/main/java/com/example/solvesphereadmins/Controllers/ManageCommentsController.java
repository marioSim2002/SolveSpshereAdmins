package com.example.solvesphereadmins.Controllers;
import com.example.solvesphereadmins.RetrievedUserData.Comment;
import com.example.solvesphereadmins.RetrievedUserData.CommentDAO;
import com.example.solvesphereadmins.RetrievedUserData.CommentDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ManageCommentsController {
    @FXML private ListView<HBox> commentsListView;
    @FXML private TextField searchField;
    @FXML private Label totalCommentsLabel;

    private final CommentDAO commentDAO = new CommentDAOImpl();
    private ObservableList<Comment> allComments = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadComments();
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
    private void handleClose() {
        ((Stage) totalCommentsLabel.getScene().getWindow()).close();
    }
}
