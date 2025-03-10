package DatabaseUnit;

import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.AlertsUnit;
import com.example.solvesphereadmins.DatabaseConnection;
import com.example.solvesphereadmins.SecurityUnit.PasswordHasher;
import com.example.solvesphereadmins.SolveShereDBConnection;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sqlQueries.AdminQueries;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final PasswordHasher passwordHasher = new PasswordHasher(); // Use the PasswordHasher

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM admin";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                admins.add(new Admin(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("status"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    @Override
    public boolean addAdmin(String username, String password, String email, String role) {
        if (adminExists(username, email)) {
            return false; // Admin already exists
        }

        String hashedPassword = passwordHasher.hashPassword(password); //hash password before saving

        String query = "INSERT INTO admin (username, password, email, status, role) VALUES (?, ?, ?, 'ACTIVE', ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, email);
            stmt.setString(4, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean adminExists(String username, String email) {
        String query = "SELECT * FROM admin WHERE username = ? OR email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); //returns true if an admin with the same username/email exists

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Admin authenticate(String username, String password) {
        String query = "SELECT * FROM admin WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");

                if (passwordHasher.verifyPassword(password, storedHashedPassword)) { // Verify password
                    return new Admin(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),  // Store hashed password
                            rs.getString("email"),
                            rs.getString("status"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if authentication fails
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
                        rs.getString("status"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateAdminStatus(int adminId, String status) {
        String query = "UPDATE admin SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, adminId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

