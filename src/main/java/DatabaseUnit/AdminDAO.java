package DatabaseUnit;

import com.example.solvesphereadmins.AdminUnit.Admin;

public interface AdminDAO {
    Admin authenticate(String username, String password);

    Admin getAdminByUsername(String username);
}
