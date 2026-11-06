package co.edu.reportes;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/estadisticas")
public class EstadisticasServlet extends HttpServlet {

    @EJB
    private ReporteService reporteService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String idioma = request.getParameter("idioma");
        if (idioma != null && !idioma.isEmpty()) {
            request.getSession().setAttribute("idioma", idioma);
        }


        List<Reporte> lista = reporteService.listar();


        long usaquen = lista.stream().filter(r -> "Usaquén".equalsIgnoreCase(r.getBarrio())).count();
        long chapinero = lista.stream().filter(r -> "Chapinero".equalsIgnoreCase(r.getBarrio())).count();
        long suba = lista.stream().filter(r -> "Suba".equalsIgnoreCase(r.getBarrio())).count();
        long kennedy = lista.stream().filter(r -> "Kennedy".equalsIgnoreCase(r.getBarrio())).count();


        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<reportesResumen>\n");
        xml.append("  <localidad nombre=\"Usaquén\"><abiertos>").append(usaquen).append("</abiertos></localidad>\n");
        xml.append("  <localidad nombre=\"Chapinero\"><abiertos>").append(chapinero).append("</abiertos></localidad>\n");
        xml.append("  <localidad nombre=\"Suba\"><abiertos>").append(suba).append("</abiertos></localidad>\n");
        xml.append("  <localidad nombre=\"Kennedy\"><abiertos>").append(kennedy).append("</abiertos></localidad>\n");
        xml.append("</reportesResumen>");


        request.setAttribute("xmlData", xml.toString());
        request.getRequestDispatcher("/xml-demo.jsp").forward(request, response);
    }
}