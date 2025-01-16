package com.crates.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CratesCreationDB {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Connessione generica al MySQL su localhost:3307 (porta custom?), utente root senza password
        String url = "jdbc:mysql://localhost:3307/";
        String user = "root";
        String pw = "";
        
        // Carichiamo il driver MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, user, pw);
             Statement stmt = conn.createStatement()) {

            // Creiamo il database crates se non esiste
            String createDbSQL = "CREATE DATABASE IF NOT EXISTS crates";
            stmt.executeUpdate(createDbSQL);
            System.out.println("Creato database 'crates'");
            
            // Usiamo il database crates
            String useDbSQL = "USE crates";
            stmt.executeUpdate(useDbSQL);
            System.out.println("In uso il database 'crates'");
            
            // Creiamo la tabella categorie (id, nome_categoria)
            String createTableCategorie =
                "CREATE TABLE IF NOT EXISTS categorie ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "nome_categoria VARCHAR(100) NOT NULL UNIQUE"
                + ") ENGINE=InnoDB;";
            stmt.executeUpdate(createTableCategorie);
            System.out.println("Creata tabella 'categorie'");
            
            // Creiamo la tabella prodotti (id, nome, descrizione, prezzo, categoria_id)
            // con FOREIGN KEY su categorie.id
            String createTableProdotti = 
                "CREATE TABLE IF NOT EXISTS prodotti ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "nome VARCHAR(100) NOT NULL, "
                + "descrizione TEXT, "
                + "prezzo DECIMAL(10,2) NOT NULL, "
                + "categoria_id INT, "
                + "FOREIGN KEY (categoria_id) REFERENCES categorie(id) ON DELETE SET NULL ON UPDATE CASCADE"
                + ") ENGINE=InnoDB;";
            stmt.executeUpdate(createTableProdotti);
            System.out.println("Creata tabella 'prodotti'");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
