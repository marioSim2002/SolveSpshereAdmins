package com.example.solvesphereadmins.Controllers;
import com.example.solvesphereadmins.AdminUnit.AdminActionLogger;
import com.example.solvesphereadmins.AdminUnit.SessionManager;
import com.example.solvesphereadmins.RetrievedUserData.CommentDAO;
import com.example.solvesphereadmins.RetrievedUserData.CommentDAOImpl;
import com.example.solvesphereadmins.RetrievedUserData.Comment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CommentDetailsController {
    @FXML private Label contentLabel;
    @FXML private Label ownerLabel;
    @FXML private Label createdAtLabel;
    @FXML private Label upvotesLabel;
    @FXML private Label downvotesLabel;
    @FXML private Label isSolutionLabel;
    @FXML private Button deleteButton;

    private Comment comment;
    private final CommentDAO commentDAO = new CommentDAOImpl();

    public void setComment(Comment comment) {
        this.comment = comment;
        contentLabel.setText(comment.getContent());
        ownerLabel.setText("User ID: " + comment.getUserId());
        createdAtLabel.setText("Created At: " + comment.getCreatedAt().toString());
        upvotesLabel.setText("Upvotes: " + comment.getUpvotes());
        downvotesLabel.setText("Downvotes: " + comment.getDownvotes());
        isSolutionLabel.setText("Solution: " + (comment.isSolution() ? "Yes" : "No"));
    }

    @FXML
    private void handleDelete() {
        if (comment != null) {
            AdminActionLogger.showPopUpWind(SessionManager.getCurrentAdmin().getId(),"COMMENT_DELETION", comment.getId(),"COMMENT");
            commentDAO.deleteComment(comment.getId());
            closeWindow();
        }
    }

    @FXML
    private void handleClose() {
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) contentLabel.getScene().getWindow()).close();
    }
}
