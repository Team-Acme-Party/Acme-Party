<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="fiestas">
    <h2>Fiestas</h2>

    <table id="fiestasTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th>Nombre</th>
	            <th>Fecha</th>
	            <th>Precio</th>
	            <th></th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${fiestas}" var="fiesta">
	            <tr>
	                <td>
	                    <c:out value="${fiesta.nombre}"/>
	                </td>
	                <td>
	                    <c:out value="${fiesta.fecha}"/>
	                </td>
	                <td>
	                    <c:out value="${fiesta.precio}"/>
	                </td>
	                <td>
	                    <button type="button" class="btn btn-default" onclick="window.location.replace('/fiestas/${fiesta.id}')">Ver detalles</button>
	                </td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	    
</petclinic:layout>
