package com.example.solvesphereadmins.RetrievedUserData;

import com.example.solvesphereadmins.AdminUnit.Comment;
import com.example.solvesphereadmins.RetrievedUserData.CommentDAO;
import com.example.solvesphereadmins.SolveShereDBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAOImpl implements CommentDAO {

    @Override
    public List<Comment> getCommentsByUserId(long userId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comments WHERE user_id = ?";

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                comments.add(new Comment(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getLong("problem_id"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return comments;
    }

    @Override
    public void deleteComment(long commentId) {
        String query = "DELETE FROM comments WHERE id = ?";

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, commentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
