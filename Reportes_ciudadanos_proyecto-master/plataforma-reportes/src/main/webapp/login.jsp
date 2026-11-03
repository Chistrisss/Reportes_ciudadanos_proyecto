<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.reportes.Util" %>
<%

    String ultimoUsuario = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if (c.getName().equals("ultimoUsuario")) {
                ultimoUsuario = c.getValue();
            }
        }
    }

    String error = request.getParameter("error");
    String mensaje = "";
    if ("1".equals(error)) {
        mensaje = "Usuario y clave son obligatorios.";
    } else if ("2".equals(error)) {
        mensaje = "Usuario o clave incorrectos.";
    } else if ("3".equals(error)) {
        mensaje = "No se pudo conectar con la base de datos.";
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión</title>
</head>
<body style="font-family: Arial, sans-serif; background-color: #f4f6f9; padding: 20px;">
<div style="max-width: 400px; margin: 40px auto; background: white; padding: 30px; border-radius: 8px; text-align: center;">
    <h2 style="color: #2c3e50;">Iniciar Sesión</h2>

    <% if (!mensaje.isEmpty()) { %>
        <p style="color: #e74c3c;"><%= mensaje %></p>
    <% } %>

    <form action="login" method="post">
        <div style="margin-bottom: 15px;">
            <input type="text" name="usuario" placeholder="Usuario"
                   value="<%= Util.escapar(ultimoUsuario) %>"
                   style="width: 100%; padding: 8px; box-sizing: border-box;">
        </div>
        <div style="margin-bottom: 15px;">
            <input type="password" name="clave" placeholder="Clave"
                   style="width: 100%; padding: 8px; box-sizing: border-box;">
        </div>
        <button type="submit" style="width: 100%; padding: 10px; background: #3498db; color: white; border: none; border-radius: 4px;">Entrar</button>
    </form>

    <br>
    <a href="index.html">Volver al inicio</a>
</div>
</body>
</html>
