package com.crates.prodotti;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.crates.crud.ProdottiCRUD;
import com.crates.classi.Prodotti;
import com.google.gson.Gson;

/**
 * Servlet implementation class VisualizzaProdottiServlet
 */
@WebServlet("/VisualizzaProdottiServlet")
public class VisualizzaProdottiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VisualizzaProdottiServlet() {
        super();
    }

    /**
     * Gestisce la richiesta GET per visualizzare tutti i prodotti.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurazione CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        response.setContentType("application/json; charset=UTF-8");

        ProdottiCRUD prodottiCRUD = new ProdottiCRUD();
        List<Prodotti> listaProdotti = prodottiCRUD.getAllProdotti();

        if (listaProdotti != null) {
            Gson gson = new Gson();
            String json = gson.toJson(listaProdotti);
            response.getWriter().write(json);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Errore durante l'accesso ai prodotti.\"}");
        }
    }

    /**
     * Se riceve un POST, lo dirottiamo su doGet (comportamento uguale).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
