package com.example.solvesphereadmins.Reports;

import com.example.solvesphereadmins.RetrievedUserData.Report;

import java.util.List;

public interface ReportDAO {
    List<Report> getReportsByProblemId(long problemId);
}
