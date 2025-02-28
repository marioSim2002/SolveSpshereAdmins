package com.example.solvesphereadmins.ServerUnit;

import DatabaseUnit.AdminDAO;
import DatabaseUnit.AdminDAOImpl;
import com.example.solvesphereadmins.AdminUnit.Admin;
import com.example.solvesphereadmins.SecurityUnit.PasswordHasher;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            out.println("Connected to Admin Server!");

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Received: " + clientMessage);

                if (clientMessage.startsWith("AUTH ")) {
                    String[] parts = clientMessage.split(" ");
                    if (parts.length == 3) {
                        String username = parts[1];
                        String password = parts[2];

                        boolean authenticated = authenticateAdmin(username, password);
                        out.println(authenticated ? "AUTH_SUCCESS" : "AUTH_FAILED");
                    } else {
                        out.println("INVALID_FORMAT");
                    }
                } else if (clientMessage.equals("EXIT")) {
                    out.println("Goodbye!");
                    break;
                } else {
                    out.println("UNKNOWN_COMMAND");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client disconnected.");
        }
    }

    private boolean authenticateAdmin(String username, String password) {
        AdminDAO adminDAO = new AdminDAOImpl();
        Admin admin = adminDAO.getAdminByUsername(username);
        PasswordHasher hasher = new PasswordHasher();
        return admin != null && hasher.hashPassword(password).equals(admin.getPassword());
    }
}
