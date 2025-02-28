package DatabaseUnit;

import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.DatabaseConnection;
public interface AdminDAO {
    boolean authenticate(String username, String password);

    Admin getAdminByUsername(String username);
}
