package com.example.solvesphereadmins.RetrievedUserData;

import java.util.List;
import java.util.Map;

public interface ProblemDAO {
    boolean addProblem(Problem problem);

    List<Problem> getProblemsByUserId(long userId);

    void deleteProblem(long problemId);

    Map<String, Long> getCategoryCountByUser(long userId);

    List<Problem> getAllProblems();

    List<Problem> findSimilarProblemsByTitleAndDescription(String titleInput, String descInput) throws ClassNotFoundException;
}
