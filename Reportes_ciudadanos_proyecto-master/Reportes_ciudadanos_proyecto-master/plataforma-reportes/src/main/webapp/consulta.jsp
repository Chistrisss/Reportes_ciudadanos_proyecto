<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.reportes.Reporte" %>
<%@ page import="co.edu.reportes.ReporteDAO" %>
<%@ page import="co.edu.reportes.Util" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Consulta de Reportes</title>
</head>
<body style="font-family: Arial; padding: 20px;">

<%@ include file="menu.jsp" %>

<h2>Consulta Rápida por Barrio</h2>

<form method="get" action="consulta.jsp">
    <input type="text" name="barrio" placeholder="Ej. Kennedy">
    <button type="submit">Buscar</button>
</form>

<%
    String barrioConsulta = request.getParameter("barrio");
    boolean hayFiltro = barrioConsulta != null && !barrioConsulta.isBlank();

    if (!hayFiltro) {
        barrioConsulta = "General (Todos los barrios)";
    }

    out.println("<p>Mostrando reportes para la zona: <strong>" + Util.escapar(barrioConsulta) + "</strong></p>");

    application.setAttribute("ultimoBarrioConsultado", barrioConsulta);

    if (barrioConsulta.equalsIgnoreCase("explotar")) {
        int fallo = 10 / 0;
    }


    ReporteDAO dao = new ReporteDAO();
    List<Reporte> resultados = hayFiltro ? dao.listarPorBarrio(barrioConsulta.trim()) : dao.listar();
%>

<ul>
<%
    if (resultados.isEmpty()) {
%>
    <li>No hay reportes para esta zona.</li>
<%
    } else {
        for (Reporte r : resultados) {
%>
    <li><%= Util.escapar(r.resumen()) %> — <small><%= Util.escapar(r.getBarrio()) %></small></li>
<%
        }
    }
%>
</ul>

<hr>
<p>El último barrio consultado globalmente fue: <strong><%= Util.escapar(String.valueOf(application.getAttribute("ultimoBarrioConsultado"))) %></strong></p>

</body>
</html>
