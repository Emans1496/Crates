package com.crates.categorie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.crates.crud.CategorieCRUD;

/**
 * Servlet implementation class EliminaCategoriaServlet
 */
@WebServlet("/EliminaCategoriaServlet")
public class EliminaCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EliminaCategoriaServlet() {
        super();
    }

    /**
     * Gestisce la richiesta POST per eliminare una categoria in base al parametro ID.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurazione CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        try {
            // Leggiamo l'ID dalla request
            int id = Integer.parseInt(request.getParameter("id"));

            CategorieCRUD categorieCRUD = new CategorieCRUD();
            boolean success = categorieCRUD.deleteCategoria(id);

            if (success) {
                response.getWriter().write("{\"success\":\"Categoria eliminata con successo.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Errore durante l'eliminazione della categoria.\"}");
            }
        } catch (NumberFormatException e) {
            // Se l'ID non è un numero valido
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Parametro ID non valido.\"}");
        }
    }

    /**
     * Gestisce la richiesta OPTIONS (preflight).
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

    /**
     * Metodo DELETE alternativo, se preferiamo usare DELETE come verbo HTTP.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Simile a doPost, ma con doDelete
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            CategorieCRUD categorieCRUD = new CategorieCRUD();
            boolean success = categorieCRUD.deleteCategoria(id);

            if (success) {
                response.getWriter().write("{\"success\":\"Categoria eliminata con successo.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Errore durante l'eliminazione della categoria.\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Parametro ID non valido.\"}");
        }
    }
}
