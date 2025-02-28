package com.example.solvesphereadmins.SecurityUnit;

import DatabaseUnit.AdminDAO;
import DatabaseUnit.AdminDAOImpl;

public class Authenticator {
    private final AdminDAO adminDAO;
    private final PasswordHasher passwordHasher;

    public Authenticator(PasswordHasher passwordHasher) {
        this.adminDAO = new AdminDAOImpl();
        this.passwordHasher = passwordHasher;
    }

    public boolean authenticate(String username, String password) {
        return adminDAO.authenticate(username, passwordHasher.hashPassword(password));
    }
}
