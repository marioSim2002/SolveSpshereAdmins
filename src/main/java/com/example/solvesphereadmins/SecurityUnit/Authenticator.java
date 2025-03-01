package com.example.solvesphereadmins.SecurityUnit;

import DatabaseUnit.AdminDAO;
import DatabaseUnit.AdminDAOImpl;
import com.example.solvesphereadmins.AdminUnit.Admin;

public class Authenticator {
    private final AdminDAO adminDAO;
    private final PasswordHasher passwordHasher;

    public Authenticator(PasswordHasher passwordHasher) {
        this.adminDAO = new AdminDAOImpl();
        this.passwordHasher = passwordHasher;
    }

    public Admin authenticate(String username, String password) {
        return adminDAO.authenticate(username,(password));
    }
}
