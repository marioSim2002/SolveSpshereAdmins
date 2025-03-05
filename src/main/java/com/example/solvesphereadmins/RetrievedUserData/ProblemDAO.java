package com.example.solvesphereadmins.RetrievedUserData;

import java.util.List;
import java.util.Map;

public interface ProblemDAO {
    List<Problem> getProblemsByUserId(long userId);

    void deleteProblem(long problemId);

    Map<String, Long> getCategoryCountByUser(long userId);
}
