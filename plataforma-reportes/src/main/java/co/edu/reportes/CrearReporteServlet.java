package co.edu.reportes;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/crear-reporte")
public class CrearReporteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Comprobar si hay un usuario logueado en la sesión
        HttpSession session = request.getSession(false);
        String usuario = (session != null) ? (String) session.getAttribute("usuarioLogueado") : null;

        if (usuario == null) {
            // Si no ha iniciado sesión, lo mandamos a loguearse
            response.sendRedirect("login.html");
            return;
        }

        // Si llegó hasta aquí, sí está logueado: atrapamos los datos
        String titulo = request.getParameter("titulo");
        String barrio = request.getParameter("barrio");

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>¡Reporte recibido con éxito!</h1>");
        response.getWriter().println("<p><b>Reportado por:</b> " + usuario + "</p>");
        response.getWriter().println("<p><b>Problema:</b> " + titulo + "</p>");
        response.getWriter().println("<p><b>Barrio:</b> " + barrio + "</p>");
        response.getWriter().println("<br><a href='reporte.html'>Registrar otro reporte</a>");
    }
}