<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="misasistencias">
    <h2>Mis solicitudes de asistencia</h2>

    <table class="table table-striped">
	        <thead>
	        <tr>
	            <th>Cliente</th>
	            <th>Fiesta</th>
	            <th>Decision</th>
	            <th></th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${asistencias}" var="asistencia">
	            <tr>
	                <td>
	                    <c:out value="${asistencia.cliente}"/>
	                </td>
	                <td>
	                    <c:out value="${asistencia.fiesta}"/>
	                </td>
	                <td>
	                    <c:out value="${asistencia.decision}"/>
	                </td>
	                <td>
	                    <button type="button" class="btn btn-default" 
	                    onclick="window.location.replace('/fiestas/${asistencia.fiesta.id}')">
	                    	Ver fiesta
	                    </button>
	                </td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	    
</petclinic:layout>
