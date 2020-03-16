<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
        <div class="col-md-12">
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
	    
	    <hr>
	    
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
            </tr>
        </c:forEach>
        </tbody>
    </table>
        
        </div>
    </div>
    
    
    
</petclinic:layout>
