package co.edu.reportes;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");


        if (usuario == null || usuario.isBlank() || clave == null || clave.isBlank()) {
            response.sendRedirect("login.jsp?error=1");
            return;
        }


        usuario = usuario.trim();
        if (!usuario.matches("[A-Za-z0-9_]{3,30}")) {
            response.sendRedirect("login.jsp?error=2");
            return;
        }

        try {

            Integer usuarioId = new UsuarioDAO().validar(usuario, clave);
            if (usuarioId == null) {
                response.sendRedirect("login.jsp?error=2");
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("usuarioId", usuarioId);
            session.setAttribute("usuario", usuario);


            Cookie cookie = new Cookie("ultimoUsuario", usuario);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);

            response.sendRedirect("index.html");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=3");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("login.jsp");
    }
}
