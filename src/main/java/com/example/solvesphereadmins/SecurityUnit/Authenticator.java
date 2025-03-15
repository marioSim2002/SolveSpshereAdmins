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
        Admin admin = adminDAO.getAdminByUsername(username); //fetch the admin's stored details
        if (admin != null && passwordHasher.verifyPassword(password, admin.getPassword())) {
            return admin; //password matches, return authenticated admin
        }

        return null; //authentication failed /no match
    }
}
