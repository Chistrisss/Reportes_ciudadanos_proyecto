<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="lang" value="${not empty sessionScope.idioma ? sessionScope.idioma : 'es_CO'}" />
<fmt:setLocale value="${lang}" scope="session" />
<fmt:setBundle basename="mensajes" />

<nav style="margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid #ccc;">
    <a href="index.jsp">🏠 <fmt:message key="menu.inicio" /></a> |
    <a href="ver-reportes">📋 <fmt:message key="menu.ver_reportes" /></a> |
    <a href="consulta.jsp">🔍 <fmt:message key="menu.buscar_barrio" /></a> |
    <a href="estadisticas">📊 <fmt:message key="menu.estadisticas" /></a> |

    <a href="consulta.jsp?idioma=es_CO" style="margin-left: 15px;">🇪🇸 Español</a> |
    <a href="consulta.jsp?idioma=en_US">🇺🇸 English</a>
</nav>