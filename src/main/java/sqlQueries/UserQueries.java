package sqlQueries;

public class UserQueries {
    public static final String SELECT_USER_ID_BY_USERNAME_AND_EMAIL = "SELECT id FROM users WHERE username = ? AND email = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM users";
    public static final String UPDATE_USER_ACTIVITY_STATUS = "UPDATE users SET ACTIVE = ? WHERE id = ?";
    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

}
