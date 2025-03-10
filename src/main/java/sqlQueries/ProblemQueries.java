package sqlQueries;

public class ProblemQueries {
    public static final String INSERT_PROBLEM_SQL =
            "INSERT INTO problems (title, description, user_id, created_at, category, is_age_restricted) VALUES (?, ?, ?, ?, ?, ?);";

}
