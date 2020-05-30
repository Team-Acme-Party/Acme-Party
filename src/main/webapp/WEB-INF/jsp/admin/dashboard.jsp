<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="${name}">
    <h2>Dashboard</h2>
    
    <h3>Locales</h3>
    <p>Locales Aceptados: <c:out value="${localA}"/>%</p>
    <p>Locales Pendientes: <c:out value="${localP}"/>%</p>
    <p>Locales Rechazados: <c:out value="${localR}"/>%</p>
    <br>
    <h3>Fiestas</h3>
    <p>Fiestas Aceptadas: <c:out value="${fiestaA}"/>%</p>
    <p>Fiestas Pendientes: <c:out value="${fiestaP}"/>%</p>
    <p>Fiestas Rechazadas: <c:out value="${fiestaR}"/>%</p>
    <br>
    <h3>Solicitudes</h3>
    <p>Solicitudes Aceptadas: <c:out value="${solicitudA}"/>%</p>
    <p>Solicitudes Pendientes: <c:out value="${solicitudP}"/>%</p>
    <p>Solicitudes Rechazadas: <c:out value="${solicitudR}"/>%</p>
	
</petclinic:layout>
