<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="locales">

    <h2>
    Información del local 
    <sec:authorize access="hasAuthority('cliente')">
    	<%-- <spring:url value="{localId}/fiestas/new" var="addUrl">
        	<spring:param name="localId" value="${local.id}"/>
	    </spring:url>
	    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Solicitar fiesta</a> --%>
	    <a href="/fiestas/new/${local.id }" class="btn btn-default">Solicitar fiesta</a>
    </sec:authorize>
    </h2>


    <table class="table table-striped">
        <tr>
            <th>Dirección</th>
            <td><b><c:out value="${local.direccion}"/></b></td>
        </tr>
        <tr>
            <th>Capacidad</th>
            <td><c:out value="${local.capacidad}"/></td>
        </tr>
        <tr>
            <th>Condiciones</th>
            <td><c:out value="${local.condiciones}"/></td>
        </tr>
        <tr>
            <th>Imagen</th>
            <td><img src="${local.imagen}" alt="${local.imagen}"/></td>
        </tr>
        <tr>
            <th>Decisión</th>
            <td><c:out value="${local.decision}"/></td>
        </tr>
        <tr>
            <th>Propietario</th>
            <td><c:out value="${local.propietario}"/></td>
        </tr>
        <tr>
        <th>
        <td>
         <button type="button" class="btn btn-default" onclick="window.location.replace('/administrador/local/${local.id}/aceptar')">ACEPTAR</button>
         <button type="button" class="btn btn-default"onclick="window.location.replace('/administrador/local/${local.id}/rechazar')">RECHAZAR</button>
       	</td>
        </tr>
    </table>
</petclinic:layout>
