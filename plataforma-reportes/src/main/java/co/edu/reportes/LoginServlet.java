package co.edu.reportes;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        // Simulación básica de validación (puedes cambiar "admin" y "1234")
        if ("admin".equals(usuario) && "1234".equals(clave)) {
            // Guardamos el usuario en la sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);

            // Redirigimos directamente a la página del formulario de reportes
            response.sendRedirect("reporte.html");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h3 style='color:red;'>Usuario o contraseña incorrectos</h3>");
            response.getWriter().println("<a href='login.html'>Intentar de nuevo</a>");
        }
    }
}