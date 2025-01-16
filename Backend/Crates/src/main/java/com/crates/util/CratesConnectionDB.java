package com.crates.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CratesConnectionDB {
    // Restituisce una connessione al database MySQL
    public static Connection getConnection() throws SQLException {
        try {
            // Carichiamo il driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        String url = "jdbc:mysql://localhost:3307/crates";
        String user = "root";
        String pw = "";

        return DriverManager.getConnection(url, user, pw);		
    }
}
