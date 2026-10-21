<%-- 1. COMENTARIO JSP: Esta página muestra la lista dinámica de reportes ciudadanos (ahora leídos desde MySQL) --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="error.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.reportes.Reporte" %>
<%@ page import="co.edu.reportes.ReporteDAO" %>
<%@ page import="co.edu.reportes.Util" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reportes Dinámicos JSP</title>
</head>
<body style="font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 0;">

<%@ include file="menu.jsp" %>

<div style="max-width: 600px; margin: 30px auto; background: white; padding: 20px; border-radius: 8px;">
    <h2 style="color: #2c3e50;">Últimos Reportes Registrados</h2>

    <%
        // SESSION: quién está usando la plataforma ahora
        String usuarioSesion = (String) session.getAttribute("usuario");

        // BASE DE DATOS: el DAO hace la consulta, la JSP solo muestra
        List<Reporte> listaReportes = new ReporteDAO().listar();
    %>

    <% if (usuarioSesion != null) { %>
        <p>Sesión iniciada como: <strong><%= Util.escapar(usuarioSesion) %></strong></p>
    <% } else { %>
        <p>No has iniciado sesión. <a href="login.jsp">Iniciar sesión</a></p>
    <% } %>

    <ul>
    <%
        if (listaReportes.isEmpty()) {
    %>
        <li style="margin-bottom: 10px; padding: 10px; background: #ecf0f1; border-left: 4px solid #e74c3c;">
            Aún no hay reportes nuevos. ¡Registra el primero!
        </li>
    <%
        } else {
            for (Reporte reporte : listaReportes) {
    %>
        <li style="margin-bottom: 10px; padding: 10px; background: #ecf0f1; border-left: 4px solid #e74c3c;">
            <%= Util.escapar(reporte.resumen()) %>
            <br>
            <small style="color: #7f8c8d;">
                Reportado por <%= Util.escapar(reporte.getUsuario()) %> · <%= reporte.getFecha() %>
                · Estado: <%= Util.escapar(reporte.getEstado()) %>
                <% if (reporte.getLatitud() != null && reporte.getLongitud() != null) { %>
                    · 📍 <%= reporte.getLatitud() %>, <%= reporte.getLongitud() %>
                <% } %>
            </small>
        </li>
    <%
            }
        }
    %>
    </ul>
</div>
</body>
</html>
