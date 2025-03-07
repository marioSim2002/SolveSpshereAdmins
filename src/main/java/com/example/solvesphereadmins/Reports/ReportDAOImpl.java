package com.example.solvesphereadmins.Reports;

import com.example.solvesphereadmins.RetrievedUserData.Report;
import com.example.solvesphereadmins.SolveShereDBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAOImpl implements ReportDAO {
    @Override
    public List<Report> getReportsByProblemId(long problemId) {
        List<Report> reports = new ArrayList<>();
        String query = "SELECT * FROM reports WHERE problem_id = ?";

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, problemId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reports.add(new Report(
                        rs.getLong("id"),
                        rs.getLong("problem_id"),
                        rs.getLong("reporter_id"),
                        rs.getString("report_reason"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reports;
    }
    @Override
    public void deleteReport(long reportId) {
        String query = "DELETE FROM reports WHERE id = ?";

        try (Connection conn = SolveShereDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, reportId);
            stmt.executeUpdate();
            System.out.println("Report deleted successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
