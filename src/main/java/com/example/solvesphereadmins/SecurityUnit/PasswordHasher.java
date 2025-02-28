package com.example.solvesphereadmins.SecurityUnit;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordHasher() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String hashPassword(String password) {
        String hashedPassword = passwordEncoder.encode(password);
        System.out.println("Generated Hash: " + hashedPassword);
        return hashedPassword;
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        boolean matches = passwordEncoder.matches(password, hashedPassword);
        System.out.println("Password Verification Result: " + matches);
        return matches;
    }
}
