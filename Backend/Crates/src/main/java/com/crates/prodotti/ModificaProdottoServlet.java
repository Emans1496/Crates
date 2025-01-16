package com.crates.prodotti;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.crates.crud.ProdottiCRUD;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.crates.classi.Prodotti;

/**
 * Servlet implementation class ModificaProdottoServlet
 */
@WebServlet("/ModificaProdottoServlet")
public class ModificaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ModificaProdottoServlet() {
        super();
    }

    /**
     * Gestisce la richiesta POST per modificare un prodotto esistente.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurazione CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        try {
            // Usando GSON e JsonObject possiamo estrarre i campi dal body JSON
            Gson gson = new Gson();
            JsonObject jsonPayload = gson.fromJson(request.getReader(), JsonObject.class);

            int id = jsonPayload.get("id").getAsInt();
            String nome = jsonPayload.get("nome").getAsString();
            String descrizione = jsonPayload.get("descrizione").getAsString();
            double prezzo = jsonPayload.get("prezzo").getAsDouble();
            int categoriaId = jsonPayload.get("categoriaId").getAsInt();

            // Creiamo un oggetto Prodotti con i dati
            Prodotti prodotto = new Prodotti(id, nome, descrizione, prezzo, categoriaId);

            // Aggiorniamo tramite ProdottiCRUD
            ProdottiCRUD prodottiCRUD = new ProdottiCRUD();
            boolean success = prodottiCRUD.updateProdotto(prodotto);

            if (success) {
                response.getWriter().write("{\"success\":\"Prodotto aggiornato con successo.\"}");
                System.out.println("Payload ricevuto: " + jsonPayload.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Errore durante l'aggiornamento del prodotto.\"}");
                System.out.println("Payload ricevuto: " + jsonPayload.toString());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Errore durante l'elaborazione della richiesta: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }

    /**
     * Gestisce la richiesta OPTIONS.
     */
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurazione CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
