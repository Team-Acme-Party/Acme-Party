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

<petclinic:layout pageName="locales">
    <h2>Locales</h2>

    <table id="localesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Dirección</th>
            <th>Capacidad</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${locales}" var="local">
            <tr>
                <td>
                    <c:out value="${local.direccion}"/>
                </td>
                <td>
                    <c:out value="${local.capacidad}"/>
                </td>
                <td>
	                    <button type="button" class="btn btn-default" onclick="window.location.replace('/locales/${local.id}')">Ver detalles</button>
	            </td>
	               <td>
	                    <button type="button" class="btn btn-default" onclick="window.location.replace('/local/${local.id}/fiestas')">Ver solicitudes</button>
	            </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
   <sec:authorize access="hasAuthority('propietario')">
		<a class="btn btn-default" href='<spring:url value="/locales/new" htmlEscape="true"/>'>Nuevo local</a>
   </sec:authorize>
	
</petclinic:layout>
