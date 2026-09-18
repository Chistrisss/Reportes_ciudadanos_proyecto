package co.edu.reportes;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReporteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>Sistema de Reportes Activo</h1>");
        response.getWriter().println("<p>El backend de la Plataforma de Reportes Ciudadanos está listo para procesar peticiones.</p>");
        response.getWriter().println("<br><a href='index.html'>Volver al mapa interactivo</a>");
    }
}