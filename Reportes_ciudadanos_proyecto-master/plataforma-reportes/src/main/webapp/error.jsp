<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Error del Sistema</title>
</head>
<body style="font-family: Arial; padding: 20px; text-align: center;">

    <h1 style="color: red;">Ocurrió un error en la plataforma</h1>
    <p>Detalle técnico del fallo:</p>

    <code><%= exception != null ? exception.getMessage() : "Error no identificado" %></code>

    <br><br>
    <a href="consulta.jsp">Volver a intentar</a>

</body>
</html>