package DatabaseUnit;

import com.example.solvesphereadmins.AdminUnit.Admin;

import java.sql.SQLException;
import java.util.List;

public interface AdminDAO {
    List<Admin> getAllAdmins();

    boolean addAdmin(String username, String password, String email, String role);

    Admin authenticate(String username, String password);

    Admin getAdminByUsername(String username);

    void updateAdminStatus(int adminId, String status);

    boolean adminExists(String username, String email);

}
