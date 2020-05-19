<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="mismensajes">

		<h2>Mensajes enviados</h2>
	    <table id="mensajesEnviadosTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th>Asunto</th>
	            <th>Fecha</th>
	            <th>Hora</th>
	            <th>Destinatario</th>
	            <th></th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${enviados}" var="mensaje">
	            <tr>
	                <td>
	                    <c:out value="${mensaje.asunto}"/>
	                </td>
	                <td>
	                    <c:out value="${mensaje.fecha}"/>
	                </td>
	                <td>
	                    <c:out value="${mensaje.hora}"/>
	                </td>
	                <td>
	                    <c:out value="${mensaje.destinatario}"/>
	                </td>
	                <td>
		            	<button type="button" class="btn btn-default" onclick="window.location.replace('/mensajes/${mensaje.id}')">Ver detalles</button>
		            </td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	    
	    <h2>Mensajes recibidos</h2>
	    <table id="mensajesEnviadosTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th>Asunto</th>
	            <th>Fecha</th>
	            <th>Hora</th>
	            <th>Remitente</th>
	            <th></th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${recibidos}" var="mensaje">
	            <tr>
	                <td>
	                    <c:out value="${mensaje.asunto}"/>
	                </td>
	                <td>
	                    <c:out value="${mensaje.fecha}"/>
	                </td>
	                <td>
	                    <c:out value="${mensaje.hora}"/>
	                </td>
	                <td>
	                    <c:out value="${mensaje.remitente}"/>
	                </td>
	                <td>
		            	<button type="button" class="btn btn-default" onclick="window.location.replace('/mensajes/${mensaje.id}')">Ver detalles</button>
		            </td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	    <a class="btn btn-default" href='<spring:url value="/mensajes/new" htmlEscape="true"/>'>Nuevo mensaje</a>
</petclinic:layout>
