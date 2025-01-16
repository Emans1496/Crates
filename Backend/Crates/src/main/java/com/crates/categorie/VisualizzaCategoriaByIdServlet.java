package com.crates.categorie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.crates.crud.CategorieCRUD;
import com.crates.classi.Categorie;
import com.google.gson.Gson;

/**
 * Servlet implementation class VisualizzaCategoriaByIdServlet
 */
@WebServlet("/VisualizzaCategoriaByIdServlet")
public class VisualizzaCategoriaByIdServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VisualizzaCategoriaByIdServlet() {
        super();
    }

    /**
     * Gestisce la richiesta GET per ottenere una categoria in base al suo ID.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurazione CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json; charset=UTF-8");

        try {
            // Recuperiamo l'ID dai parametri della query string
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("ID mancante nella richiesta");
            }
            int id = Integer.parseInt(idParam);

            // Interroghiamo il database
            CategorieCRUD categorieCRUD = new CategorieCRUD();
            Categorie categoria = categorieCRUD.getCategoriaById(id);

            if (categoria != null) {
                Gson gson = new Gson();
                String json = gson.toJson(categoria);
                response.getWriter().write(json);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Categoria non trovata\"}");
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Errore durante il recupero della categoria\"}");
        }
    }
}
