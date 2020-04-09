<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="locales">

	<c:forEach items="${anuncios}" var="anuncio">
		<img src="${anuncio.imagen }" alt="${anuncio.imagen }" height="150" width="100%"/>
	</c:forEach>
	<br>

    <h2>
	    Informacion del local 
	    <c:if test="${local.decision == 'ACEPTADO' }">
		    <sec:authorize access="hasAuthority('cliente')">
		    	<a href="/fiestas/new/${local.id}" class="btn btn-default">Solicitar fiesta</a>
		    </sec:authorize>
		    <sec:authorize access="hasAuthority('patrocinador')">
		    	<a href="/anuncio/new/${local.id}/local" class="btn btn-default">Ofrecer anuncio</a>
		    </sec:authorize>
	    </c:if>
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
        <sec:authorize access="hasAuthority('admin')">
        	<c:if test="${local.decision == 'PENDIENTE'}">
		        <tr>	        
			        <td>
			         <button type="button" class="btn btn-default" onclick="window.location.replace('/administrador/local/${local.id}/aceptar')">ACEPTAR</button>
			         <button type="button" class="btn btn-default"onclick="window.location.replace('/administrador/local/${local.id}/rechazar')">RECHAZAR</button>
			       	</td>
		        </tr>
	        </c:if>
        </sec:authorize>
    </table>
   
    <h5>
    	Comentarios
    </h5>
    <table id="comentariosTable" class="table table-striped">
        <thead>
        <tr>
            <th>Cuerpo</th>
            <th>Fecha</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${comentarios}" var="comentario">
            <tr>
                <td>
                    <c:out value="${comentario.cuerpo}"/>
                </td>
                <td>
                    <c:out value="${comentario.fecha}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <h5>
    	Valoraciones
    </h5>
    <table id="valoracionesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Comentario</th>
            <th>Valoración</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${valoraciones}" var="valoracion">
            <tr>
                <td>
                    <c:out value="${valoracion.comentario}"/>
                </td>
                <td>
                    <c:out value="${valoracion.valor}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
