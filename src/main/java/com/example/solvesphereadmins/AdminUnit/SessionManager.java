package com.example.solvesphereadmins.AdminUnit;

public class SessionManager {
    private static Admin currentAdmin;

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }
}
