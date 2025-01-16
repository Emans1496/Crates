package com.crates.prodotti;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

import com.crates.crud.ProdottiCRUD;
import com.google.gson.Gson;
import com.crates.classi.Prodotti;

/**
 * Servlet implementation class AggiungiProdottoServlet
 */
@WebServlet("/AggiungiProdottoServlet")
public class AggiungiProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore di default.
     */
    public AggiungiProdottoServlet() {
        super();
    }

    /**
     * Gestisce la richiesta POST per aggiungere un nuovo prodotto nel database.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurazione CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        try {
            // Leggiamo il corpo della richiesta come JSON
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            // Convertiamo il JSON in oggetto Prodotti
            Gson gson = new Gson();
            Prodotti prodotto = gson.fromJson(sb.toString(), Prodotti.class);

            // Aggiungiamo il prodotto tramite ProdottiCRUD
            ProdottiCRUD prodottiCRUD = new ProdottiCRUD();
            boolean success = prodottiCRUD.addProdotto(prodotto);

            if (success) {
                response.getWriter().write("{\"success\":\"Prodotto aggiunto con successo.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Errore durante l'aggiunta del prodotto.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Eventuale errore di parsing o altro
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Richiesta non valida.\"}");
        }
    }

    /**
     * Gestisce la richiesta OPTIONS (preflight).
     */
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
