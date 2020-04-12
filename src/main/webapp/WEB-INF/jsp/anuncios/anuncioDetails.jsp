<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="anuncioDetails">

    <h2>Información del anuncio</h2>


    <table class="table table-striped">
        <tr>
            <th>Patrocinador</th>
            <td><b><c:out value="${anuncio.patrocinador}"/></b></td>
        </tr>
        <c:if test="${anuncio.fiesta != null }">
        	<tr>
            	<th>Fiesta</th>
            <td><c:out value="${anuncio.fiesta}"/></td>
        </tr>
        </c:if>
        <c:if test="${anuncio.local != null }">
        	<tr>
            	<th>Local</th>
            <td><c:out value="${anuncio.local}"/></td>
        </tr>
        </c:if>
        <tr>
            <th>Decision</th>
            <td><c:out value="${anuncio.decision}"/></td>
        </tr>

        <tr>
            <th>Imagen</th>
            <td><img src="${anuncio.imagen}" alt="${anuncio.imagen}"/></td>
        </tr>
        <sec:authorize access="hasAuthority('cliente')">
        	<c:if test="${anuncio.decision == 'PENDIENTE'}">
		        <tr>	        
			        <td>
			         <button type="button" class="btn btn-default" onclick="window.location.replace('/cliente/anuncio/${anuncio.id}/aceptar')">ACEPTAR</button>
			         <button type="button" class="btn btn-default"onclick="window.location.replace('/cliente/anuncio/${anuncio.id}/rechazar')">RECHAZAR</button>
			       	</td>
		        </tr>
	        </c:if>
        </sec:authorize>
        <sec:authorize access="hasAuthority('propietario')">
        	<c:if test="${anuncio.decision == 'PENDIENTE'}">
		        <tr>	        
			        <td>
			         <button type="button" class="btn btn-default" onclick="window.location.replace('/propietario/anuncio/${anuncio.id}/aceptar')">ACEPTAR</button>
			         <button type="button" class="btn btn-default"onclick="window.location.replace('/propietario/anuncio/${anuncio.id}/rechazar')">RECHAZAR</button>
			       	</td>
		        </tr>
	        </c:if>
        </sec:authorize>
        
     </table>   
</petclinic:layout>
