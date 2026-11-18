<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Iniciar Sesión - Plataforma Ciudadana</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .auth-card {
            background: #ffffff;
            width: 100%;
            max-width: 400px;
            padding: 32px;
            border-radius: 8px;
            border-top: 5px solid #9e0c92;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        }
        .auth-card h1 {
            margin: 0 0 6px 0;
            font-size: 1.5rem;
            color: #111827;
        }
        .auth-card p {
            margin: 0 0 22px 0;
            color: #6b7280;
            font-size: 0.92rem;
        }
        .form-group {
            margin-bottom: 16px;
        }
        .form-group label {
            display: block;
            font-weight: 600;
            font-size: 0.9rem;
            color: #374151;
            margin-bottom: 6px;
        }
        .form-group input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #cbd5e1;
            border-radius: 6px;
            font-size: 0.95rem;
            box-sizing: border-box;
        }
        .form-group input:focus {
            outline: none;
            border-color: #9e0c92;
            box-shadow: 0 0 0 3px rgba(158, 12, 146, 0.15);
        }
        .btn-submit {
            width: 100%;
            background-color: #9e0c92;
            color: #ffffff;
            border: none;
            padding: 11px;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            margin-top: 6px;
        }
        .btn-submit:hover {
            background-color: #7d0973;
        }
        .alert-error {
            background-color: #fef2f2;
            color: #b91c1c;
            border: 1px solid #fecaca;
            padding: 10px 12px;
            border-radius: 6px;
            font-size: 0.88rem;
            margin-bottom: 16px;
        }
        .alert-success {
            background-color: #fae8f8;
            color: #9e0c92;
            border: 1px solid #f3c6ef;
            padding: 10px 12px;
            border-radius: 6px;
            font-size: 0.88rem;
            margin-bottom: 16px;
            font-weight: 600;
        }
        .auth-footer {
            margin-top: 20px;
            text-align: center;
            font-size: 0.9rem;
            color: #4b5563;
        }
        .auth-footer a {
            color: #9e0c92;
            font-weight: 600;
            text-decoration: none;
        }
        .auth-footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="auth-card">
    <h1>Iniciar Sesión</h1>
    <p>Ingresa con tu usuario y contraseña</p>

    <c:if test="${param.registrado == '1'}">
        <div class="alert-success">Cuenta creada correctamente. Ya puedes iniciar sesión.</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert-error"><c:out value="${error}" /></div>
    </c:if>

    <form action="auth" method="post">
        <input type="hidden" name="accion" value="login" />

        <div class="form-group">
            <label for="nombre_usuario">Nombre de usuario</label>
            <input type="text" id="nombre_usuario" name="nombre_usuario" required placeholder="Ej. ciudadano" />
        </div>

        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password" required placeholder="Tu contraseña" />
        </div>

        <button type="submit" class="btn-submit">Ingresar</button>
    </form>

    <div class="auth-footer">
        ¿Aún no tienes cuenta? <a href="registro.jsp">Regístrate aquí</a><br /><br />
        <a href="index.jsp">Volver al inicio</a>
    </div>
</div>

</body>
</html>