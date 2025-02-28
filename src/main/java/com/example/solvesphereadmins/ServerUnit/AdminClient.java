package com.example.solvesphereadmins.ServerUnit;

import java.io.*;
import java.net.Socket;

public class AdminClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server: " + in.readLine());

            while (true) {
                System.out.print("Enter command (AUTH username password / EXIT): ");
                String command = userInput.readLine();
                out.println(command);

                String response = in.readLine();
                System.out.println("Server Response: " + response);

                if (response.equals("Goodbye!")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
