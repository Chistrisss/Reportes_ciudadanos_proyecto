package co.edu.reportes;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/reporte")
public class ReporteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String titulo = request.getParameter("titulo");
        String barrio = request.getParameter("barrio");
        String descripcion = request.getParameter("descripcion");

        out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'><title>Respuesta del Servidor</title></head>");
        out.println("<body style='font-family: Arial; text-align: center; margin-top: 50px;'>");

        if (titulo == null || titulo.trim().isEmpty() ||
                barrio == null || barrio.trim().isEmpty() ||
                descripcion == null || descripcion.trim().isEmpty()) {

            out.println("<h2 style='color: #e74c3c;'>Error: Validación Fallida</h2>");
            out.println("<p>Todos los campos son obligatorios. No se permiten datos vacíos.</p>");
            out.println("<a href='reporte.html' style='color: #3498db;'>Volver al formulario</a>");

        } else {
            out.println("<h2 style='color: #2ecc71;'>¡Reporte Procesado Correctamente!</h2>");
            out.println("<div style='border: 1px solid #ccc; padding: 20px; display: inline-block; text-align: left; background: #f9f9f9;'>");
            out.println("<p><strong>Título:</strong> " + titulo + "</p>");
            out.println("<p><strong>Barrio de Bogotá:</strong> " + barrio + "</p>");
            out.println("<p><strong>Descripción:</strong> " + descripcion + "</p>");
            out.println("</div><br><br>");
            out.println("<a href='reporte.html' style='color: #3498db;'>Registrar otro reporte</a>");
        }

        out.println("</body></html>");
    }
}