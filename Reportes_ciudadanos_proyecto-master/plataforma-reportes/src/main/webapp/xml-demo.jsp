<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Forzar la actualización del idioma en la sesión al hacer clic en los botones --%>
<c:if test="${not empty param.idioma}">
    <c:set var="idioma" value="${param.idioma}" scope="session" />
</c:if>
<c:set var="lang" value="${not empty sessionScope.idioma ? sessionScope.idioma : 'es_CO'}" />
<fmt:setLocale value="${lang}" scope="session" />
<fmt:setBundle basename="mensajes" />

<!DOCTYPE html>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="stats.titulo" /></title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<header>
    <h1><fmt:message key="stats.titulo" /></h1>
    <p><fmt:message key="stats.subtitulo" /></p>

    <nav style="margin-top: 15px;">
        <a href="index.jsp">🏠 <fmt:message key="menu.inicio" /></a> |
        <a href="ver-reportes">📋 <fmt:message key="menu.ver_reportes" /></a> |
        <a href="consulta.jsp">🔍 <fmt:message key="menu.buscar_barrio" /></a> |
        <a href="estadisticas">📊 <fmt:message key="menu.estadisticas" /></a> |

        <a href="estadisticas?idioma=es_CO" style="margin-left: 15px;">🇪🇸 Español</a> |
        <a href="estadisticas?idioma=en_US">🇺🇸 English</a>
    </nav>
</header>

<main class="contenedor-principal" style="padding: 20px; max-width: 900px; margin: 0 auto;">

    <%-- Parseo del documento XML generado por el Servlet --%>
    <x:parse xml="${xmlData}" var="doc" />

    <section style="background: #f4f6f8; padding: 20px; border-radius: 6px; margin-top: 20px;">
        <h2 style="color: #333; margin-top: 0;"><fmt:message key="stats.resumen" /></h2>

        <ul style="font-size: 1.1em; line-height: 1.8; color: #555;">
            <li><strong>Usaquén:</strong> <span style="color: #d9534f; font-weight: bold;"><x:out select="$doc/reportesResumen/localidad[@nombre='Usaquén']/abiertos" /></span> <fmt:message key="stats.reportes_vigentes" />.</li>
            <li><strong>Chapinero:</strong> <span style="color: #d9534f; font-weight: bold;"><x:out select="$doc/reportesResumen/localidad[@nombre='Chapinero']/abiertos" /></span> <fmt:message key="stats.reportes_vigentes" />.</li>
            <li><strong>Suba:</strong> <span style="color: #d9534f; font-weight: bold;"><x:out select="$doc/reportesResumen/localidad[@nombre='Suba']/abiertos" /></span> <fmt:message key="stats.reportes_vigentes" />.</li>
            <li><strong>Kennedy:</strong> <span style="color: #d9534f; font-weight: bold;"><x:out select="$doc/reportesResumen/localidad[@nombre='Kennedy']/abiertos" /></span> <fmt:message key="stats.reportes_vigentes" />.</li>
        </ul>
    </section>

</main>
</body>
</html>