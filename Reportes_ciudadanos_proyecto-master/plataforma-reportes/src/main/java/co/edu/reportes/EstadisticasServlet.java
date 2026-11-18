package co.edu.reportes;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/estadisticas")
public class EstadisticasServlet extends HttpServlet {

    @Inject
    private ReporteService reporteService;

    private static final List<String> LOCALIDADES_BOGOTA = Arrays.asList(
            "Usaquén", "Chapinero", "Santa Fe", "San Cristóbal", "Usme",
            "Tunjuelito", "Bosa", "Kennedy", "Fontibón", "Engativá",
            "Suba", "Barrios Unidos", "Teusaquillo", "Los Mártires",
            "Antonio Nariño", "Puente Aranda", "La Candelaria",
            "Rafael Uribe Uribe", "Ciudad Bolívar", "Sumapaz"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Reporte> lista = reporteService.listar();
        int totalGeneral = lista.size();

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<reportesResumen total=\"").append(totalGeneral).append("\">\n");

        for (String loc : LOCALIDADES_BOGOTA) {
            long conteo = lista.stream()
                    .filter(r -> r.getBarrio() != null && loc.equalsIgnoreCase(r.getBarrio().trim()))
                    .count();

            int porcentaje = (totalGeneral > 0) ? (int) Math.round((conteo * 100.0) / totalGeneral) : 0;

            xml.append("  <localidad nombre=\"").append(loc).append("\">\n");
            xml.append("    <abiertos>").append(conteo).append("</abiertos>\n");
            xml.append("    <porcentaje>").append(porcentaje).append("</porcentaje>\n");
            xml.append("  </localidad>\n");
        }

        xml.append("</reportesResumen>");

        request.setAttribute("xmlData", xml.toString());
        request.getRequestDispatcher("/xml-demo.jsp").forward(request, response);
    }
}