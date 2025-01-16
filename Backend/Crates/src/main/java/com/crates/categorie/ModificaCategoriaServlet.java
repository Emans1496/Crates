package com.crates.categorie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

import com.crates.crud.CategorieCRUD;
import com.crates.classi.Categorie;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class ModificaCategoriaServlet
 */
@WebServlet("/ModificaCategoriaServlet")
public class ModificaCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ModificaCategoriaServlet() {
        super();
    }

    /**
     * Gestisce la richiesta POST per modificare una categoria.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurazione CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        try {
            // Leggiamo il JSON inviato
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Convertiamo in JSON e preleviamo campi ID e nomeCategoria
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);

            if (jsonObject == null || !jsonObject.has("id") || !jsonObject.has("nomeCategoria")) {
                throw new IllegalArgumentException("ID o nomeCategoria mancante nel JSON");
            }

            int id = jsonObject.get("id").getAsInt();
            String nomeCategoria = jsonObject.get("nomeCategoria").getAsString();

            // Creiamo l'oggetto categoria e aggiorniamo
            Categorie categoria = new Categorie(id, nomeCategoria);
            CategorieCRUD categorieCRUD = new CategorieCRUD();
            boolean success = categorieCRUD.updateCategoria(categoria);

            if (success) {
                response.getWriter().write("{\"success\":\"Categoria modificata con successo.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Errore durante la modifica della categoria.\"}");
            }
        } catch (IllegalArgumentException e) {
            // Gestione di campi mancanti
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Errore durante l'elaborazione della richiesta.\"}");
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
        response.setHeader("Access-Control-Max-Age", "3600"); // Cache preflight
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
