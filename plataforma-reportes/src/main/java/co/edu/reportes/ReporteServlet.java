package co.edu.reportes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ReporteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String titulo = request.getParameter("titulo");
        String barrio = request.getParameter("barrio");
        String descripcion = request.getParameter("descripcion");

        out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'><title>Respuesta</title></head>");
        out.println("<body style='font-family: Arial; text-align: center; margin-top: 50px;'>");

        if (titulo == null || titulo.trim().isEmpty() ||
                barrio == null || barrio.trim().isEmpty() ||
                descripcion == null || descripcion.trim().isEmpty()) {

            out.println("<h2 style='color: #e74c3c;'>Error: Validación Fallida</h2>");
            out.println("<p>Todos los campos son obligatorios.</p>");
            out.println("<a href='reporte.html'>Volver al formulario</a>");

        } else {

            String reporteArmado = titulo + " - Barrio " + barrio + " (" + descripcion + ")";


            HttpSession session = request.getSession();
            List<String> miLista = (List<String>) session.getAttribute("listaReportes");

            if (miLista == null) {
                miLista = new ArrayList<>();
            }

            miLista.add(reporteArmado);
            session.setAttribute("listaReportes", miLista);

            out.println("<h2 style='color: #2ecc71;'>¡Reporte Procesado y Guardado!</h2>");
            out.println("<p>Tu reporte ha sido añadido a la lista dinámica.</p>");
            out.println("<br>");
            out.println("<a href='reportes-dinamicos.jsp' style='padding: 10px 20px; background: #3498db; color: white; text-decoration: none; border-radius: 5px; font-weight: bold;'>Ver tabla dinámica de reportes (JSP)</a>");
        }

        out.println("</body></html>");
    }
}