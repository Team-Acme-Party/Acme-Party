<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="misanuncios">
	<c:if test="${isPatrocinador || isCliente }">
		<c:if test="${isPatrocinador}">
			<c:set var="anuncios" value="${paraFiestas}"/>
		</c:if>
		<c:if test="${isCliente}">
			<c:set var="anuncios" value="${anuncios}"/>
		</c:if>
		<h2>Anuncios hechos a fiestas</h2>

	    <table id="anunciosTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th>Fiesta</th>
	            <th>Decision</th>
	            <th></th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${anuncios}" var="anuncio">
	            <tr>
	                <td>
	                    <c:out value="${anuncio.fiesta}"/>
	                </td>
	                <td>
	                    <c:out value="${anuncio.decision}"/>
	                </td>
	                <td>
		            	<button type="button" class="btn btn-default" onclick="window.location.replace('/anuncios/${anuncio.id}')">Ver detalles</button>
		            </td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	    <hr>
	</c:if>
		
	<c:if test="${isPatrocinador || isPropietario}">
		<c:if test="${isPatrocinador}">
			<c:set var="anuncios" value="${paraLocales}"/>
		</c:if>
		<c:if test="${isPropietario}">
			<c:set var="anuncios" value="${anuncios}"/>
		</c:if>
		<h2>Anuncios hechos a locales</h2>

	    <table id="anunciosTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th>Local</th>
	            <th>Decision</th>
	            <th></th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${anuncios}" var="anuncio">
	            <tr>
	                <td>
	                    <c:out value="${anuncio.local}"/>
	                </td>
	                <td>
	                    <c:out value="${anuncio.decision}"/>
	                </td>
	                <td>
		            	<button type="button" class="btn btn-default" onclick="window.location.replace('/anuncios/${anuncio.id}')">Ver detalles</button>
		            </td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	</c:if>	
     
</petclinic:layout>
