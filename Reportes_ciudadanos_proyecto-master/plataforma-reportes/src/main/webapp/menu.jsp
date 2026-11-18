<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="lang" value="${not empty sessionScope.idioma ? sessionScope.idioma : 'es_CO'}" />
<fmt:setLocale value="${lang}" scope="session" />
<fmt:setBundle basename="mensajes" />

<nav style="margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid #ccc;">
    <a href="index.jsp">Inicio</a> |
    <a href="ver-reportes">Ver Reportes</a> |
    <a href="consulta.jsp">Buscar Barrio</a> |
    <a href="estadisticas">Estadísticas</a> |
    <a href="reportes-jsf.xhtml">Registro Rápido</a> |
    <a href="ciclo-vida-reporte.xhtml">Alertas por Barrio</a> |
</nav>

    <a href="consulta.jsp?idioma=es_CO" style="margin-left: 15px;">🇪🇸 Español</a> |
    <a href="consulta.jsp?idioma=en_US">🇺🇸 English</a>
</nav>