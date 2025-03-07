package sqlQueries;

public class AdminQueries {

    public static final String GET_ADMIN_SCRIPT = "SELECT * FROM admin WHERE username=? AND password=?";
    public static final String DELETE_ADMIN_ACTION  = "DELETE FROM admin_actions WHERE id = ?";
    public static final String GET_ADMIN_ACT_BY_ID = "SELECT * FROM admin_actions WHERE admin_id = ? ORDER BY timestamp DESC";
    public static final String GET_ALL_ADMINS_ACTS = "SELECT * FROM admin_actions ORDER BY timestamp DESC";
    public static final String INSERT_ADMIN_ACT_SCRIPT ="INSERT INTO admin_actions (admin_id, action_type, target_id, target_type, description) VALUES (?, ?, ?, ?, ?)";
}
