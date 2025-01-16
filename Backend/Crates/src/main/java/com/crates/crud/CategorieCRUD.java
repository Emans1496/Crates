package com.crates.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.crates.classi.Categorie;
import com.crates.util.CratesConnectionDB;

public class CategorieCRUD {

    // Metodo per ottenere tutte le categorie
    public List<Categorie> getAllCategorie() {
        String query = "SELECT * FROM categorie";
        List<Categorie> listaCategorie = new ArrayList<>();

        try (Connection conn = CratesConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nomeCategoria = rs.getString("nome_categoria");

                Categorie categoria = new Categorie(id, nomeCategoria);
                listaCategorie.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaCategorie;
    }

    // Metodo per aggiungere una categoria
    public boolean addCategoria(Categorie categoria) {
        String query = "INSERT INTO categorie (nome_categoria) VALUES (?)";
        try (Connection conn = CratesConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, categoria.getNomeCategoria());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Metodo per aggiornare una categoria
    public boolean updateCategoria(Categorie categoria) {
        String query = "UPDATE categorie SET nome_categoria = ? WHERE id = ?";
        try (Connection conn = CratesConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, categoria.getNomeCategoria());
            stmt.setInt(2, categoria.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Metodo per eliminare una categoria
    public boolean deleteCategoria(int id) {
        String query = "DELETE FROM categorie WHERE id = ?";
        try (Connection conn = CratesConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

	public Categorie getCategoriaById(int id) {
        String query = "SELECT * FROM categorie WHERE id = ?";
        try (Connection conn = CratesConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nomeCategoria = rs.getString("nome_categoria");
                    return new Categorie(id, nomeCategoria);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
