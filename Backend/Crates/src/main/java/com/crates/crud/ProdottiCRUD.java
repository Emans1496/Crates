package com.crates.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.crates.classi.Prodotti;
import com.crates.util.CratesConnectionDB;
import com.crates.util.LoggerUtil;

public class ProdottiCRUD {

    // Ritorna tutti i prodotti presenti in DB
    public List<Prodotti> getAllProdotti() {
        String query = "SELECT * FROM prodotti";
        List<Prodotti> listaProdotti = new ArrayList<>();

        try (Connection conn = CratesConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Leggiamo i prodotti dal result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");
                double prezzo = rs.getDouble("prezzo");
                int categoriaId = rs.getInt("categoria_id");

                Prodotti prodotto = new Prodotti(id, nome, descrizione, prezzo, categoriaId);
                listaProdotti.add(prodotto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProdotti;
    }

    // Aggiunge un nuovo prodotto al DB
    public boolean addProdotto(Prodotti prodotto) {
        String query = "INSERT INTO prodotti (nome, descrizione, prezzo, categoria_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = CratesConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, prodotto.getNome());
            stmt.setString(2, prodotto.getDescrizione());
            stmt.setDouble(3, prodotto.getPrezzo());
            stmt.setInt(4, prodotto.getCategoriaId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Aggiorna un prodotto esistente
    public boolean updateProdotto(Prodotti prodotto) {
        String query = "UPDATE prodotti SET nome = ?, descrizione = ?, prezzo = ?, categoria_id = ? WHERE id = ?";
        try (Connection conn = CratesConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, prodotto.getNome());
            stmt.setString(2, prodotto.getDescrizione());
            stmt.setDouble(3, prodotto.getPrezzo());
            stmt.setInt(4, prodotto.getCategoriaId());
            stmt.setInt(5, prodotto.getId());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                // Log dell'operazione
                LoggerUtil.log("Prodotto modificato: ID = " + prodotto.getId() +
                               ", Nome = " + prodotto.getNome() +
                               ", Prezzo = " + prodotto.getPrezzo());
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Elimina un prodotto tramite il suo ID
    public boolean deleteProdotto(int id) {
        String query = "DELETE FROM prodotti WHERE id = ?";
        try (Connection conn = CratesConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                // Log dell'operazione
                LoggerUtil.log("Prodotto eliminato: ID = " + id);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Ritorna un Prodotto in base al suo ID
    public Prodotti getProdottoById(int id) {
        String query = "SELECT * FROM prodotti WHERE id = ?";
        try (Connection conn = CratesConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String descrizione = rs.getString("descrizione");
                    double prezzo = rs.getDouble("prezzo");
                    int categoriaId = rs.getInt("categoria_id");

                    return new Prodotti(id, nome, descrizione, prezzo, categoriaId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Se non trovato, restituiamo null
        return null;
    }
}
