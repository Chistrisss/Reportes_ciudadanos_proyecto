<%-- 1. COMENTARIO JSP: Esta página muestra la lista dinámica de reportes ciudadanos --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

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

            List<String> listaReportes = (List<String>) session.getAttribute("listaReportes");


            if (listaReportes == null || listaReportes.isEmpty()) {
                listaReportes = new ArrayList<>();
                listaReportes.add("Aún no hay reportes nuevos. ¡Registra el primero!");
            }
        %>

        <ul>
            <%
                for(String reporte : listaReportes) {
            %>
                    <li style="margin-bottom: 10px; padding: 10px; background: #ecf0f1; border-left: 4px solid #e74c3c;">
                        <%= reporte %>
                    </li>
            <%
                }
            %>
        </ul>
    </div>
</body>
</html>