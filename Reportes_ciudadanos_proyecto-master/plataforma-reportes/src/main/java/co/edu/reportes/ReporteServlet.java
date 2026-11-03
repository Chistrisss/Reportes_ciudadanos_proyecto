package co.edu.reportes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.naming.InitialContext;

@WebServlet({"/ReporteServlet", "/reporte"})
public class ReporteServlet extends HttpServlet {

    @EJB
    private ReporteService reporteService;

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


        if (usuarioId == null) { errores.add("Tu sesión expiró. Debes iniciar sesión para publicar un reporte."); }
        if (titulo.isEmpty()) { errores.add("El tipo de problema es obligatorio."); }
        else if (titulo.length() > 120) { errores.add("El tipo de problema no puede superar 120 caracteres."); }

        if (barrio.isEmpty()) { errores.add("La localidad es obligatoria."); }
        else if (!LOCALIDADES.contains(barrio)) { errores.add("Por favor selecciona una localidad válida de la lista sugerida."); }

        if (descripcion.isEmpty()) { errores.add("La descripción es obligatoria."); }
        else if (descripcion.length() > 500) { errores.add("La descripción no puede superar 500 caracteres."); }

        Double latitud = null;
        Double longitud = null;
        if (latTxt.isEmpty() || lngTxt.isEmpty()) {
            errores.add("Haz clic en el mapa de Bogotá para elegir la ubicación antes de enviar.");
        } else {
            try {
                latitud = Double.parseDouble(latTxt);
                longitud = Double.parseDouble(lngTxt);
            } catch (NumberFormatException e) {
                errores.add("Las coordenadas recibidas no son válidas.");
            }
        }


        if (!errores.isEmpty()) {
            int estado = (usuarioId == null) ? 401 : 400;
            if (ajax) { enviarTexto(response, estado, String.join("\n", errores)); }
            else { paginaError(response, errores); }
            return;
        }


        try {
            if (reporteService == null) {
                InitialContext ic = new InitialContext();
                reporteService = (ReporteService) ic.lookup("java:module/ReporteService");
            }

            Reporte reporte = new Reporte(titulo, barrio, descripcion, latitud, longitud);
            reporte.setUsuarioId(usuarioId);

            reporteService.guardar(reporte, titulo);

            if (ajax) { enviarTexto(response, 200, "OK"); }
            else { paginaExito(response); }
        } catch (Exception e) {
            e.printStackTrace();
            String mensaje = "Error crítico de base de datos: " + e.getMessage();
            if (ajax) { enviarTexto(response, 500, mensaje); }
            else { paginaError(response, List.of(mensaje)); }
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
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Respuesta</title></head>");
        out.println("<body style='text-align: center; margin-top: 50px;'><h2 style='color: green;'>¡Reporte Guardado!</h2><a href='reportes-dinamicos.jsp'>Ver tabla dinámica</a></body></html>");
    }

    private void paginaError(HttpServletResponse response, List<String> errores) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Error</title></head>");
        out.println("<body style='text-align: center; margin-top: 50px;'><h2>Se encontraron errores:</h2>");
        for (String error : errores) { out.println("<p style='color: red;'>" + error + "</p>"); }
        out.println("<a href='index.html'>Volver al formulario</a></body></html>");
    }
}