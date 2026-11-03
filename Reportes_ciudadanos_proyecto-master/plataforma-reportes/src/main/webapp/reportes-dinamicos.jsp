<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.reportes.Reporte" %>
<%@ page import="co.edu.reportes.ReporteService" %>
<%@ page import="javax.naming.InitialContext" %>
<%
    List<Reporte> listaReportes = null;
    try {
        InitialContext ic = new InitialContext();
        ReporteService service = (ReporteService) ic.lookup("java:module/ReporteService");
        listaReportes = service.listar();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reportes Registrados</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <h1>Últimos Reportes Registrados</h1>
    <p>Listado dinámico desde la base de datos MySQL</p>
    <nav>
        <a href="index.html">Volver al Inicio (Mapa)</a> |
        <a href="index.html#crear-reporte">Registrar Reporte</a> |
        <a href="reportes-dinamicos.jsp">Ver Reportes</a> |
        <a href="consulta.jsp">Buscar Barrio</a>
    </nav>
</header>

<main style="max-width: 800px; margin: 0 auto; padding: 20px;">
    <% if (listaReportes == null || listaReportes.isEmpty()) { %>
    <p style="text-align: center; color: #7f8c8d;">No hay reportes registrados aún en la base de datos.</p>
    <% } else { %>
    <% for (Reporte reporte : listaReportes) { %>
    <div style="border-left: 5px solid #e74c3c; background: #ecf0f1; padding: 15px; margin-bottom: 15px; border-radius: 4px;">
        <h3 style="margin-top: 0; color: #2c3e50;">
            <%= reporte.getTitulo() != null ? reporte.getTitulo() : "Sin título" %>
            - <%= reporte.getBarrio() != null ? reporte.getBarrio() : "Ubicación desconocida" %>
        </h3>
        <p style="margin: 8px 0; color: #34495e;">
            <%= reporte.getDescripcion() != null ? reporte.getDescripcion() : "" %>
        </p>
        <small style="color: #7f8c8d; font-size: 0.85em;">
            <strong>Estado:</strong> <%= reporte.getEstado() %> |
            <strong>Fecha:</strong> <%= reporte.getFecha() %> |
            <strong>Coordenadas:</strong> <%= reporte.getLatitud() %>, <%= reporte.getLongitud() %>
        </small>
    </div>
    <% } %>
    <% } %>
</main>
</body>
</html>