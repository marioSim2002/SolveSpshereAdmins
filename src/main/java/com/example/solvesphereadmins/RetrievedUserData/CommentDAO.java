package com.example.solvesphereadmins.RetrievedUserData;

import com.example.solvesphereadmins.RetrievedUserData.Comment;

import java.util.List;

public interface CommentDAO {
    List<Comment> getCommentsByUserId(long userId);

    void deleteComment(long commentId);

    List<Comment> getAllComments();
}
