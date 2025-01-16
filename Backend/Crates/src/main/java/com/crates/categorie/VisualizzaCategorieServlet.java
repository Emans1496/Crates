package com.crates.categorie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.crates.crud.CategorieCRUD;
import com.crates.classi.Categorie;
import com.google.gson.Gson;

/**
 * Servlet implementation class VisualizzaCategorieServlet
 */
@WebServlet("/VisualizzaCategorieServlet")
public class VisualizzaCategorieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VisualizzaCategorieServlet() {
        super();
    }

    /**
     * Gestisce la richiesta GET per visualizzare tutte le categorie esistenti.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurazione CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        CategorieCRUD categorieCRUD = new CategorieCRUD();
        List<Categorie> listaCategorie = categorieCRUD.getAllCategorie();

        if (listaCategorie != null) {
            Gson gson = new Gson();
            String json = gson.toJson(listaCategorie);
            response.getWriter().write(json);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Errore durante l'accesso alle categorie.\"}");
        }
    }

    /**
     * Se viene fatto un POST su questa servlet, chiamiamo doGet
     * (anche se potrebbe non essere necessario).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
