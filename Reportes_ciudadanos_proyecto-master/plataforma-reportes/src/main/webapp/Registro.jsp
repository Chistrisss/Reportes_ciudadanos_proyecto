<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body style="font-family: sans-serif; background: #f0f2f5; display: flex; justify-content: center; padding-top: 60px;">
<div style="background: white; padding: 30px; border-radius: 8px; border-top: 5px solid #9e0c92; width: 320px;">
    <h2 style="margin-top: 0;">Crear Cuenta</h2>
    <% if (request.getParameter("error") != null) { %>
    <p style="color: red;">Ese usuario ya existe.</p>
    <% } %>
    <form action="auth" method="post">
        <input type="hidden" name="accion" value="registrar">
        <label>Usuario:</label><br>
        <input type="text" name="usuario" required style="width: 100%; padding: 8px; margin: 6px 0 14px;"><br>
        <label>Contraseña:</label><br>
        <input type="password" name="clave" required style="width: 100%; padding: 8px; margin: 6px 0 18px;"><br>
        <button type="submit" style="width: 100%; background: #9e0c92; color: white; border: none; padding: 10px; border-radius: 5px; cursor: pointer;">Registrarme</button>
    </form>
    <p style="text-align: center; margin-top: 15px;"><a href="login.jsp">Ya tengo cuenta</a> | <a href="index.jsp">Inicio</a></p>
</div>
</body>
</html>