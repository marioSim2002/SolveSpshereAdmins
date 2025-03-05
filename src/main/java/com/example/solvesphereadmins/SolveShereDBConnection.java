package com.example.solvesphereadmins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SolveShereDBConnection {

    ///DB connection///
    //// DON'T PUSH NEW URL/USER/PASS ////
    private static final String URL = "jdbc:mysql://localhost:3306/solvespheredata";
    private static final String USER = "root";
    private static final String PASSWORD = "mario123";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");  // register MySQL driver
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

