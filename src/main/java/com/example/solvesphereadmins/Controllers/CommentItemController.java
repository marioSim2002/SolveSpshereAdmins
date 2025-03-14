package com.example.solvesphereadmins.Controllers;

import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.RetrievedUserData.Comment;
import com.example.solvesphereadmins.RetrievedUserData.CommentDAO;
import com.example.solvesphereadmins.RetrievedUserData.CommentDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CommentItemController {
    @FXML private Label contentLabel;
    @FXML private Label ownerLabel;
    @FXML private Label createdAtLabel;

    private Comment comment;
    private ManageCommentsController parentController;
    private final CommentDAO commentDAO = new CommentDAOImpl();
    public void setComment(Comment comment, ManageCommentsController parentController ) {
        this.comment = comment;
        this.parentController = parentController;
        contentLabel.setText(comment.getContent());
        ownerLabel.setText("User ID: " + comment.getUserId());
        createdAtLabel.setText("Created At: " + comment.getCreatedAt().toString());
    }

    @FXML
    private void handleViewDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/solvesphereadmins/CommentDetails.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Comment Details");

            CommentDetailsController controller = loader.getController();
            controller.setComment(comment);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        commentDAO.deleteComment(comment.getId());
        parentController.refreshCommentList();
    }
}
