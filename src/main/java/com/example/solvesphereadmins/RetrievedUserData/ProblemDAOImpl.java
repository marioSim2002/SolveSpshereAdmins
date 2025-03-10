package com.example.solvesphereadmins.RetrievedUserData;

import com.example.solvesphereadmins.SolveShereDBConnection;
import sqlQueries.ProblemQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemDAOImpl implements ProblemDAO {

    // data access obj for problems operations  //

    @Override
    public boolean addProblem(Problem problem) {
        return false;
    }

    @Override
    public List<Problem> getProblemsByUserId(long userId) {
        List<Problem> problems = new ArrayList<>();
        String query = "SELECT * FROM problems WHERE user_id = ?";

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                problems.add(new Problem(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getTimestamp("created_at"),
                        rs.getBoolean("is_age_restricted")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return problems;
    }

    @Override
    public void deleteProblem(long problemId) {
        String query = "DELETE FROM problems WHERE id = ?";

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, problemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Long> getCategoryCountByUser(long userId) {
        String query = "SELECT category, COUNT(*) as count FROM problems WHERE user_id = ? GROUP BY category";
        Map<String, Long> categoryCount = new HashMap<>();

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {  // ✅ Iterate through ResultSet properly
                categoryCount.put(rs.getString("category"), rs.getLong("count"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categoryCount;
    }

    @Override
    public List<Problem> getAllProblems() {
        List<Problem> problems = new ArrayList<>();
        String query = "SELECT id, user_id, title, description, category, created_at, is_age_restricted FROM problems";

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            var rs = stmt.executeQuery();
            while (rs.next()) {
                problems.add(new Problem(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getTimestamp("created_at"),
                        rs.getBoolean("is_age_restricted")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return problems;
    }

    @Override
    public List<Problem> findSimilarProblemsByTitleAndDescription(String titleInput, String descInput) throws ClassNotFoundException {
        List<Problem> similarProblems = new ArrayList<>();

        // Dynamic query building
        String sql = "SELECT id, user_id, title, description, category, created_at, is_age_restricted " +
                "FROM problems WHERE 1=1 ";

        if (!titleInput.isEmpty()) {
            sql += "AND INSTR(title, ?) > 0 ";
        }
        if (!descInput.isEmpty()) {
            sql += "AND INSTR(description, ?) > 0 ";
        }

        sql += "ORDER BY created_at DESC LIMIT 5"; // Fetch recent 5 problems

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (!titleInput.isEmpty()) {
                stmt.setString(paramIndex++, titleInput);
            }
            if (!descInput.isEmpty()) {
                stmt.setString(paramIndex++, descInput);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Problem problem = new Problem(
                        rs.getLong("id"),               // ID
                        rs.getLong("user_id"),          // User ID
                        rs.getString("title"),          // Title
                        rs.getString("description"),    // Description
                        rs.getString("category"),       // Category
                        rs.getTimestamp("created_at"),  // Created At
                        rs.getBoolean("is_age_restricted") // Age Restriction
                );
                similarProblems.add(problem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return similarProblems;
    }
}
