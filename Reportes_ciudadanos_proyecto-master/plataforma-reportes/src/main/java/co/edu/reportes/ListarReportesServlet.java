package co.edu.reportes;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ver-reportes")
public class ListarReportesServlet extends HttpServlet {

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


        request.setAttribute("listaReportes", lista);


        request.getRequestDispatcher("/reportes-dinamicos.jsp").forward(request, response);
    }
}