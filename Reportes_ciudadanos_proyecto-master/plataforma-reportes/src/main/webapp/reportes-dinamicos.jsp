<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="titulo.pagina" /></title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<header>
    <h1><fmt:message key="titulo.reportes" /></h1>
    <p><fmt:message key="subtitulo.reportes" /></p>

    <nav style="margin-top: 15px;">
        <a href="index.jsp"><fmt:message key="menu.inicio" /></a> |
        <a href="ver-reportes"><fmt:message key="menu.ver_reportes" /></a> |
        <a href="consulta.jsp"><fmt:message key="menu.buscar_barrio" /></a> |
        <a href="estadisticas"><fmt:message key="menu.estadisticas" /></a> |
        <a href="reportes-jsf.xhtml">Reportes Rápidos</a> |
        <a href="ciclo-vida-reporte.xhtml">Alertas por Localidad</a> |

        <a href="ver-reportes?idioma=es_CO" style="margin-left: 15px;">Español</a> |
        <a href="ver-reportes?idioma=en_US">English</a>
    </nav>
</header>

<main class="contenedor-principal" style="padding: 20px; max-width: 900px; margin: 0 auto;">

    <c:set var="mensajeFiltro"><fmt:message key="filtro.activo" /></c:set>
    <p style="text-align: center; color: #666; font-style: italic; margin-bottom: 20px;">
        <c:out value="${mensajeFiltro}" />
    </p>
    <c:remove var="mensajeFiltro" />

    <section id="historial-reportes">
        <c:choose>
            <c:when test="${empty listaReportes}">
                <div class="tarjeta-reporte" style="text-align: center; padding: 20px; background: #ffffff; border: 1px dashed #9e0c92; border-radius: 6px;">
                    <p><fmt:message key="reportes.vacio" /></p>
                </div>
            </c:when>

            <c:otherwise>
                <c:forEach var="reporte" items="${listaReportes}">
                    <div class="tarjeta-reporte" style="background: #ffffff; border: 1px solid #e5e7eb; border-left: 5px solid #9e0c92; margin-bottom: 18px; padding: 18px; border-radius: 6px; box-shadow: 0 1px 3px rgba(0,0,0,0.06);">
                        <h2 style="margin-top: 0; margin-bottom: 8px; color: #1f2937; font-size: 1.2rem;">
                            <c:out value="${reporte.titulo}" /> - <span style="color: #9e0c92;"><c:out value="${reporte.barrio}" /></span>
                        </h2>

                        <p style="color: #4b5563; line-height: 1.45; margin-bottom: 12px;">
                            <c:out value="${reporte.descripcion}" />
                        </p>

                        <div class="detalles-reporte" style="font-size: 0.88em; color: #6b7280; padding-top: 10px; border-top: 1px solid #f3f4f6;">
                            <strong><fmt:message key="reporte.estado" />:</strong>
                            <span style="color: #d97706; font-weight: 600;"><c:out value="${reporte.estado}" /></span> |

                            <strong><fmt:message key="reporte.fecha" />:</strong>
                            <fmt:formatDate value="${reporte.fecha}" pattern="dd/MM/yyyy HH:mm" /> |

                            <strong><fmt:message key="reporte.coordenadas" />:</strong>
                            <c:out value="${reporte.latitud}" />, <c:out value="${reporte.longitud}" />
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </section>

</main>

<footer>
    <p style="text-align: center; color: #666; margin-top: 30px;">
        <fmt:message key="pie.pagina" />
    </p>
</footer>

</body>
</html>