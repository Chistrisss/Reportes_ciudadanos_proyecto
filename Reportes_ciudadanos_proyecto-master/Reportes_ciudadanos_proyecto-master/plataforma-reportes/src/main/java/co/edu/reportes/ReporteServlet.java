package co.edu.reportes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet({"/ReporteServlet", "/reporte"})
public class ReporteServlet extends HttpServlet {


    private static final List<String> LOCALIDADES = Arrays.asList(
            "Usaquén", "Chapinero", "Santa Fe", "San Cristóbal", "Usme",
            "Tunjuelito", "Bosa", "Kennedy", "Fontibón", "Engativá",
            "Suba", "Barrios Unidos", "Teusaquillo", "Los Mártires",
            "Antonio Nariño", "Puente Aranda", "La Candelaria",
            "Rafael Uribe Uribe", "Ciudad Bolívar", "Sumapaz"
    );

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        boolean ajax = "fetch".equals(request.getHeader("X-Requested-With"));


        String titulo = limpiar(request.getParameter("titulo"));
        String barrio = limpiar(request.getParameter("barrio"));
        String descripcion = limpiar(request.getParameter("descripcion"));
        String latTxt = limpiar(request.getParameter("latitud"));
        String lngTxt = limpiar(request.getParameter("longitud"));

        HttpSession session = request.getSession();
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");


        List<String> errores = new ArrayList<>();

        if (usuarioId == null) {
            errores.add("Debes iniciar sesión para publicar un reporte.");
        }

        if (titulo.isEmpty()) {
            errores.add("El tipo de problema es obligatorio.");
        } else if (titulo.length() > 120) {
            errores.add("El tipo de problema no puede superar 120 caracteres.");
        }

        if (barrio.isEmpty()) {
            errores.add("La localidad es obligatoria.");
        } else if (!LOCALIDADES.contains(barrio)) {
            errores.add("Por favor selecciona una localidad válida de la lista sugerida.");
        }

        if (descripcion.isEmpty()) {
            errores.add("La descripción es obligatoria.");
        } else if (descripcion.length() > 500) {
            errores.add("La descripción no puede superar 500 caracteres.");
        }

        Double latitud = null;
        Double longitud = null;
        if (latTxt.isEmpty() || lngTxt.isEmpty()) {
            errores.add("Haz clic en el mapa de Bogotá para elegir la ubicación antes de enviar.");
        } else {
            try {
                latitud = Double.parseDouble(latTxt);
                longitud = Double.parseDouble(lngTxt);
                boolean dentroDeBogota = latitud >= 3.7 && latitud <= 4.95
                        && longitud >= -74.5 && longitud <= -73.9;
                if (!dentroDeBogota) {
                    errores.add("Las coordenadas están fuera de Bogotá.");
                }
            } catch (NumberFormatException e) {
                errores.add("Las coordenadas no son válidas. Haz clic en el mapa para fijar el punto.");
            }
        }

        if (!errores.isEmpty()) {
            int estado = (usuarioId == null) ? 401 : 400;
            if (ajax) {
                enviarTexto(response, estado, String.join("\n", errores));
            } else {
                paginaError(response, errores, usuarioId == null);
            }
            return;
        }

        try {
            Reporte reporte = new Reporte(titulo, barrio, descripcion, latitud, longitud);
            new ReporteDAO().guardar(reporte, usuarioId);

            if (ajax) {
                enviarTexto(response, 200, "OK");
            } else {
                paginaExito(response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String mensaje = "No se pudo guardar el reporte en la base de datos.";
            if (ajax) {
                enviarTexto(response, 500, mensaje);
            } else {
                paginaError(response, List.of(mensaje), false);
            }
        }
    }

    private String limpiar(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private void enviarTexto(HttpServletResponse response, int estado, String texto) throws IOException {
        response.setStatus(estado);
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().print(texto);
    }

    private void paginaExito(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'><title>Respuesta</title></head>");
        out.println("<body style='font-family: Arial; text-align: center; margin-top: 50px;'>");
        out.println("<h2 style='color: #2ecc71;'>¡Reporte Procesado y Guardado!</h2>");
        out.println("<p>Tu reporte ha sido añadido a la lista dinámica.</p>");
        out.println("<br>");
        out.println("<a href='reportes-dinamicos.jsp' style='padding: 10px 20px; background: #3498db; color: white; text-decoration: none; border-radius: 5px; font-weight: bold;'>Ver tabla dinámica de reportes (JSP)</a>");
        out.println("</body></html>");
    }

    private void paginaError(HttpServletResponse response, List<String> errores, boolean sinSesion) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'><title>Respuesta</title></head>");
        out.println("<body style='font-family: Arial; text-align: center; margin-top: 50px;'>");
        out.println("<h2 style='color: #e74c3c;'>Error: Validación Fallida</h2>");
        for (String error : errores) {
            out.println("<p>" + error + "</p>");
        }
        if (sinSesion) {
            out.println("<a href='login.jsp'>Iniciar sesión</a> | ");
        }
        out.println("<a href='index.html'>Volver al formulario</a>");
        out.println("</body></html>");
    }
}