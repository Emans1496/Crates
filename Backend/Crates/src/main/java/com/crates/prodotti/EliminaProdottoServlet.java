package com.crates.prodotti;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.crates.crud.ProdottiCRUD;

/**
 * Servlet implementation class EliminaProdottoServlet
 */
@WebServlet("/EliminaProdottoServlet")
public class EliminaProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EliminaProdottoServlet() {
        super();
    }

    /**
     * Gestisce la richiesta POST per eliminare un prodotto in base all'ID.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurazione CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        // Leggiamo il parametro ID
        int id = Integer.parseInt(request.getParameter("id"));

        ProdottiCRUD prodottiCRUD = new ProdottiCRUD();
        boolean success = prodottiCRUD.deleteProdotto(id);

        if (success) {
            response.getWriter().write("{\"success\":\"Prodotto eliminato con successo.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Errore durante l'eliminazione del prodotto.\"}");
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
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Metodo DELETE alternativo.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Stessa logica di doPost, ma con verbo DELETE
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        ProdottiCRUD prodottiCRUD = new ProdottiCRUD();
        boolean success = prodottiCRUD.deleteProdotto(id);

        if (success) {
            response.getWriter().write("{\"success\":\"Prodotto eliminato con successo.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Errore durante l'eliminazione del prodotto.\"}");
        }
    }
}
