package co.edu.reportes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/crear-reporte")
public class CrearReporteServlet extends HttpServlet {

    // ¡Aquí está la magia! Esta lista es nuestra Base de Datos temporal
    private static List<Reporte> listaReportes = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Identificar al usuario logueado
        HttpSession session = request.getSession(false);
        String usuario = (session != null && session.getAttribute("usuarioLogueado") != null)
                ? (String) session.getAttribute("usuarioLogueado")
                : "Ciudadano Anónimo";

        // 2. Atrapar los datos del formulario
        String titulo = request.getParameter("titulo");
        String barrio = request.getParameter("barrio");

        // 3. Crear el objeto y guardarlo en la lista
        Reporte nuevoReporte = new Reporte(titulo, barrio, usuario);
        listaReportes.add(nuevoReporte);

        // 4. Responder dibujando una tabla con el historial de reportes
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'><title>Historial de Reportes</title>");
        out.println("<style>body{font-family: Arial; background: #f4f6f9; padding: 20px;} table{width: 100%; max-width: 800px; margin: 20px auto; border-collapse: collapse; background: white;} th, td{border: 1px solid #ddd; padding: 12px; text-align: left;} th{background-color: #3498db; color: white;} h2{text-align: center; color: #2c3e50;} .btn{display: block; width: 200px; margin: 20px auto; padding: 10px; text-align: center; background: #2ecc71; color: white; text-decoration: none; border-radius: 5px; font-weight: bold;}</style>");
        out.println("</head><body>");

        out.println("<h2>¡Reporte guardado exitosamente!</h2>");
        out.println("<h3 style='text-align:center;'>Total de reportes activos: " + listaReportes.size() + "</h3>");

        out.println("<table>");
        out.println("<tr><th>Usuario</th><th>Problema</th><th>Barrio</th></tr>");

        // Un ciclo for para imprimir cada reporte guardado en la lista
        for (Reporte r : listaReportes) {
            out.println("<tr>");
            out.println("<td>" + r.getUsuario() + "</td>");
            out.println("<td>" + r.getTitulo() + "</td>");
            out.println("<td>" + r.getBarrio() + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<a href='reporte.html' class='btn'>Volver a reportar</a>");
        out.println("</body></html>");
    }
}