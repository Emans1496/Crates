package com.crates.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet per testare la connessione al DB.
 */
@WebServlet("/CratesConnectionTryDB")
public class CratesConnectionTryDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CratesConnectionTryDB() {
        super();
    }

	/**
	 * GET che prova a connettersi al DB e mostra un messaggio di successo/fallimento.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection conn = CratesConnectionDB.getConnection()) {
			if (conn != null && !conn.isClosed()) {
				response.getWriter().println("<p>Connessione al DB riuscita!</p>");
			} else {
				response.getWriter().println("<p>Connessione al DB fallita!</p>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println("Ci sono errori nella connessione al DB " + e.getMessage());
		}
	}

	/**
	 * Se arriva una POST, la gestiamo come una GET.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
