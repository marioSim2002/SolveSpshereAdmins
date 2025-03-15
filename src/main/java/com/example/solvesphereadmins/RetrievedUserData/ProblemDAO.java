package com.example.solvesphereadmins.RetrievedUserData;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProblemDAO {

    boolean addAdminProblem(Problem problem) throws SQLException, ClassNotFoundException;

    List<Problem> getProblemsByUserId(long userId);

    void deleteProblem(long problemId);

    void deleteAdminProblem(long problemId);

    Map<String, Long> getCategoryCountByUser(long userId);

    List<Problem> getAllProblems();

    List<Problem> findSimilarProblemsByTitleAndDescription(String titleInput, String descInput) throws ClassNotFoundException;

    List<Problem> getAdminProblems();
}
