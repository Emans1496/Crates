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

/**
 * Servlet implementation class AggiungiCategoriaServlet
 */
@WebServlet("/AggiungiCategoriaServlet")
public class AggiungiCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore di default.
     */
    public AggiungiCategoriaServlet() {
        super();
    }

    /**
     * Gestisce la richiesta POST per aggiungere una nuova categoria nel database.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurazione CORS per permettere richieste da Angular (localhost:4200)
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Indichiamo che la risposta sarà in JSON con charset UTF-8
        response.setContentType("application/json; charset=UTF-8");

        try {
            // Leggiamo il JSON inviato nella richiesta
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            // Convertiamo il JSON in un oggetto Categorie tramite GSON
            Gson gson = new Gson();
            Categorie categoria = gson.fromJson(sb.toString(), Categorie.class);

            // Creiamo un'istanza di CategorieCRUD per eseguire l'operazione di insert
            CategorieCRUD categorieCRUD = new CategorieCRUD();
            boolean success = categorieCRUD.addCategoria(categoria);

            if (success) {
                response.getWriter().write("{\"success\":\"Categoria aggiunta con successo.\"}");
            } else {
                // In caso di errore lato server, settiamo lo status a 500 (INTERNAL_SERVER_ERROR)
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Errore durante l'aggiunta della categoria.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Se qualcosa va storto nella richiesta, restituiamo status 400 e un messaggio di errore
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Richiesta non valida.\"}");
        }
    }

    /**
     * Gestisce la richiesta OPTIONS (preflight) per CORS.
     */
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Stesse intestazioni CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
