<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.ArrayList, javax.naming.InitialContext, co.edu.reportes.ReporteService, co.edu.reportes.Reporte" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="es_CO" scope="session" />
<fmt:setBundle basename="mensajes" />

<%
    String barrioBuscado = request.getParameter("barrio");
    List<Reporte> reportesFiltrados = new ArrayList<>();

    try {
        InitialContext ic = new InitialContext();
        ReporteService dao = (ReporteService) ic.lookup("java:module/ReporteService");
        List<Reporte> todos = dao.listar();

        if (barrioBuscado != null && !barrioBuscado.trim().isEmpty()) {
            String filtro = barrioBuscado.trim().toLowerCase();
            for (Reporte r : todos) {
                if (r.getBarrio() != null && r.getBarrio().toLowerCase().contains(filtro)) {
                    reportesFiltrados.add(r);
                }
            }
        } else {
            reportesFiltrados = todos;
        }
    } catch (Exception e) {
        request.setAttribute("errorConsulta", e.getMessage());
    }

    request.setAttribute("reportesFiltrados", reportesFiltrados);
    request.setAttribute("totalResultados", reportesFiltrados.size());
    request.setAttribute("barrioActual", (barrioBuscado != null) ? barrioBuscado.trim() : "");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Consulta por Barrio - Plataforma de Reportes Ciudadanos</title>
    <link rel="stylesheet" href="styles.css" />
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            color: #1f2937;
            margin: 0;
            padding: 0;
        }
        .topbar {
            background-color: #9e0c92;
            color: #ffffff;
            padding: 24px 30px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.14);
        }
        .topbar-inner {
            max-width: 1050px;
            margin: 0 auto;
        }
        .topbar h1 {
            margin: 0 0 6px 0;
            font-size: 1.7rem;
        }
        .topbar p {
            margin: 0 0 16px 0;
            color: #fae8f8;
            font-size: 0.96rem;
        }
        .nav-bar {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            align-items: center;
            gap: 12px;
            padding-top: 12px;
            border-top: 1px solid rgba(255, 255, 255, 0.25);
        }
        .nav-links a {
            color: #ffffff;
            text-decoration: none;
            margin-right: 16px;
            font-size: 0.95rem;
            opacity: 0.9;
        }
        .nav-links a:hover, .nav-links a.active {
            opacity: 1;
            font-weight: 700;
            text-decoration: underline;
        }
        .container {
            max-width: 1050px;
            margin: 28px auto;
            padding: 0 20px;
        }
        .search-panel {
            background: #ffffff;
            padding: 22px;
            border-radius: 8px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.08);
            margin-bottom: 22px;
        }
        .search-panel h2 {
            margin: 0 0 14px 0;
            font-size: 1.2rem;
            color: #0f172a;
        }
        .search-form {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }
        .search-input {
            flex: 1;
            min-width: 240px;
            padding: 10px 14px;
            border: 1px solid #cbd5e1;
            border-radius: 6px;
            font-size: 1rem;
        }
        .search-input:focus {
            outline: none;
            border-color: #9e0c92;
            box-shadow: 0 0 0 3px rgba(158, 12, 146, 0.15);
        }
        .btn-primary {
            background-color: #9e0c92;
            color: #ffffff;
            border: none;
            padding: 10px 20px;
            border-radius: 6px;
            font-size: 0.95rem;
            font-weight: 600;
            cursor: pointer;
        }
        .btn-primary:hover {
            background-color: #7d0973;
        }
        .btn-secondary {
            background-color: #e2e8f0;
            color: #334155;
            text-decoration: none;
            padding: 10px 16px;
            border-radius: 6px;
            font-size: 0.95rem;
            font-weight: 600;
            display: inline-flex;
            align-items: center;
        }
        .btn-secondary:hover {
            background-color: #cbd5e1;
        }
        .results-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 16px;
            padding: 0 4px;
        }
        .results-header span {
            font-size: 0.98rem;
            color: #334155;
        }
        .count-badge {
            background-color: #fae8f8;
            color: #9e0c92;
            padding: 4px 12px;
            border-radius: 999px;
            font-weight: 600;
            font-size: 0.88rem;
            border: 1px solid #f3c6ef;
        }
        .cards-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(310px, 1fr));
            gap: 16px;
        }
        .report-card {
            background: #ffffff;
            border-radius: 8px;
            padding: 18px;
            border: 1px solid #e5e7eb;
            border-left: 4px solid #9e0c92;
            box-shadow: 0 1px 3px rgba(0,0,0,0.08);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .locality-tag {
            display: inline-block;
            background-color: #fae8f8;
            color: #9e0c92;
            font-size: 0.8rem;
            font-weight: 700;
            padding: 4px 10px;
            border-radius: 4px;
            margin-bottom: 10px;
            width: fit-content;
            border: 1px solid #f3c6ef;
        }
        .report-title {
            font-size: 1.05rem;
            font-weight: 700;
            color: #111827;
            margin: 0 0 8px 0;
        }
        .report-desc {
            font-size: 0.92rem;
            color: #4b5563;
            line-height: 1.45;
            margin: 0;
        }
        .empty-state {
            background: #ffffff;
            padding: 35px;
            text-align: center;
            border-radius: 8px;
            color: #64748b;
            border: 1px dashed #9e0c92;
        }
    </style>
</head>
<body>

<header class="topbar">
    <div class="topbar-inner">
        <h1>Consulta Rápida por Barrio</h1>
        <p>Filtra y explora las incidencias reportadas por localidad en Bogotá</p>
        <div class="nav-bar">
            <nav class="nav-links">
                <a href="index.jsp">Inicio</a>
                <a href="ver-reportes">Ver Reportes</a>
                <a href="consulta.jsp" class="active">Buscar Barrio</a>
                <a href="estadisticas">Estadísticas</a>
                <a href="reportes-jsf.xhtml">Reportes Rápidos</a>
                <a href="ciclo-vida-reporte.xhtml">Alertas por Localidad</a>
            </nav>
        </div>
    </div>
</header>

<main class="container">

    <section class="search-panel">
        <h2>Filtrar por Localidad o Sector</h2>
        <form action="consulta.jsp" method="get" class="search-form">
            <input type="text"
                   name="barrio"
                   list="localidades-bogota"
                   value="<c:out value='${barrioActual}' />"
                   placeholder="Escribe o selecciona una localidad (Ej. Kennedy, Suba, Usaquén)..."
                   class="search-input" />

            <datalist id="localidades-bogota">
                <option value="Usaquén" />
                <option value="Chapinero" />
                <option value="Santa Fe" />
                <option value="San Cristóbal" />
                <option value="Usme" />
                <option value="Tunjuelito" />
                <option value="Bosa" />
                <option value="Kennedy" />
                <option value="Fontibón" />
                <option value="Engativá" />
                <option value="Suba" />
                <option value="Barrios Unidos" />
                <option value="Teusaquillo" />
                <option value="Los Mártires" />
                <option value="Antonio Nariño" />
                <option value="Puente Aranda" />
                <option value="La Candelaria" />
                <option value="Rafael Uribe Uribe" />
                <option value="Ciudad Bolívar" />
                <option value="Sumapaz" />
            </datalist>

            <button type="submit" class="btn-primary">Buscar</button>
            <c:if test="${not empty barrioActual}">
                <a href="consulta.jsp" class="btn-secondary">Ver todos</a>
            </c:if>
        </form>
    </section>

    <div class="results-header">
            <span>
                Mostrando reportes para:
                <strong>
                    <c:choose>
                        <c:when test="${not empty barrioActual}">
                            <c:out value="${barrioActual}" />
                        </c:when>
                        <c:otherwise>
                            General (Todas las localidades)
                        </c:otherwise>
                    </c:choose>
                </strong>
            </span>
        <span class="count-badge">${totalResultados} resultados</span>
    </div>

    <c:choose>
        <c:when test="${empty reportesFiltrados}">
            <div class="empty-state">
                <p>No se encontraron reportes registrados para <strong><c:out value="${barrioActual}" /></strong>.</p>
                <a href="consulta.jsp" class="btn-secondary" style="margin-top: 10px;">Mostrar todos los reportes</a>
            </div>
        </c:when>
        <c:otherwise>
            <section class="cards-grid">
                <c:forEach var="rep" items="${reportesFiltrados}">
                    <article class="report-card">
                        <div>
                            <span class="locality-tag"><c:out value="${rep.barrio}" /></span>
                            <h3 class="report-title"><c:out value="${rep.titulo}" /></h3>
                            <p class="report-desc"><c:out value="${rep.descripcion}" /></p>
                        </div>
                    </article>
                </c:forEach>
            </section>
        </c:otherwise>
    </c:choose>

</main>

</body>
</html>