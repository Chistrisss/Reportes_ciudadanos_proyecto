<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<fmt:setLocale value="es_CO" scope="session" />
<fmt:setBundle basename="mensajes" />

<x:parse xml="${xmlData}" var="doc" />

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Estadísticas de Reportes - Plataforma Ciudadana</title>
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
        .summary-banner {
            background: #ffffff;
            padding: 22px 26px;
            border-radius: 8px;
            border-left: 5px solid #9e0c92;
            box-shadow: 0 1px 4px rgba(0,0,0,0.08);
            margin-bottom: 24px;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            align-items: center;
            gap: 16px;
        }
        .summary-banner h2 {
            margin: 0 0 4px 0;
            font-size: 1.25rem;
            color: #111827;
        }
        .summary-banner p {
            margin: 0;
            color: #6b7280;
            font-size: 0.92rem;
        }
        .total-box {
            background-color: #fae8f8;
            border: 1px solid #f3c6ef;
            padding: 12px 22px;
            border-radius: 8px;
            text-align: center;
        }
        .total-number {
            display: block;
            font-size: 1.8rem;
            font-weight: 800;
            color: #9e0c92;
            line-height: 1.1;
        }
        .total-label {
            font-size: 0.82rem;
            font-weight: 600;
            color: #6b7280;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(235px, 1fr));
            gap: 16px;
        }
        .stat-card {
            background: #ffffff;
            border-radius: 8px;
            padding: 18px;
            border: 1px solid #e5e7eb;
            border-top: 3px solid #9e0c92;
            box-shadow: 0 1px 3px rgba(0,0,0,0.07);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .stat-top {
            display: flex;
            justify-content: space-between;
            align-items: baseline;
            margin-bottom: 10px;
        }
        .stat-locality {
            font-size: 1rem;
            font-weight: 700;
            color: #1f2937;
        }
        .stat-count {
            font-size: 1.25rem;
            font-weight: 800;
            color: #9e0c92;
        }
        .progress-track {
            width: 100%;
            height: 7px;
            background-color: #f3f4f6;
            border-radius: 999px;
            overflow: hidden;
            margin-bottom: 10px;
        }
        .progress-fill {
            height: 100%;
            background-color: #9e0c92;
            border-radius: 999px;
        }
        .stat-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 0.82rem;
            color: #6b7280;
        }
        .stat-link {
            color: #9e0c92;
            text-decoration: none;
            font-weight: 600;
        }
        .stat-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<header class="topbar">
    <div class="topbar-inner">
        <h1>Estadísticas de Reportes</h1>
        <p>Monitoreo en tiempo real de los problemas registrados por localidad en Bogotá</p>
        <div class="nav-bar">
            <nav class="nav-links">
                <a href="index.jsp">Inicio</a>
                <a href="ver-reportes">Ver Reportes</a>
                <a href="consulta.jsp">Buscar Barrio</a>
                <a href="estadisticas" class="active">Estadísticas</a>
                <a href="reportes-jsf.xhtml">Reportes Rápidos</a>
                <a href="ciclo-vida-reporte.xhtml">Alertas por Localidad</a>
            </nav>
        </div>
    </div>
</header>

<main class="container">

    <section class="summary-banner">
        <div>
            <h2>Consolidado Distrital por Localidad</h2>
            <p>Distribución de incidencias vigentes extraídas dinámicamente mediante procesamiento XML y XPath</p>
        </div>
        <div class="total-box">
            <span class="total-number"><x:out select="$doc/reportesResumen/@total" /></span>
            <span class="total-label">Reportes Activos</span>
        </div>
    </section>

    <section class="stats-grid">
        <x:forEach select="$doc/reportesResumen/localidad" var="loc">
            <c:set var="nombreLoc"><x:out select="$loc/@nombre" /></c:set>
            <c:set var="cantLoc"><x:out select="$loc/abiertos" /></c:set>
            <c:set var="pctLoc"><x:out select="$loc/porcentaje" /></c:set>

            <article class="stat-card">
                <div>
                    <div class="stat-top">
                        <span class="stat-locality">${nombreLoc}</span>
                        <span class="stat-count">${cantLoc}</span>
                    </div>
                    <div class="progress-track">
                        <div class="progress-fill" style="width: ${pctLoc}%;"></div>
                    </div>
                </div>
                <div class="stat-footer">
                    <span>${pctLoc}% del total</span>
                    <a href="consulta.jsp?barrio=${nombreLoc}" class="stat-link">Ver reportes</a>
                </div>
            </article>
        </x:forEach>
    </section>

</main>

</body>
</html>