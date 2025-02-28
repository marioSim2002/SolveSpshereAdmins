package DatabaseUnit;

import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.DatabaseConnection;
import sqlQueries.AdminQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO {
    @Override
    public boolean authenticate(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(AdminQueries.GET_ADMIN_SCRIPT)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // hash before comparing in production
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public Admin getAdminByUsername(String username) {
        String query = "SELECT * FROM admin WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
