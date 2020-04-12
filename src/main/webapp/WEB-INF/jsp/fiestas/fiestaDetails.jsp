<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="fiestaDetails">

	<c:forEach items="${anuncios}" var="anuncio">
		<img src="${anuncio.imagen }" alt="${anuncio.imagen }" height="150" width="100%"/>
	</c:forEach>
	<br>

    <h2>
	    Informacion de la fiesta 
	    <c:if test="${fiesta.decision == 'ACEPTADO' }">
		    <sec:authorize access="hasAuthority('patrocinador')">
		    	<a href="/anuncio/new/${fiesta.id}/fiesta" class="btn btn-default">Ofrecer anuncio</a>
		    </sec:authorize>
	    </c:if>
    </h2>

    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${fiesta.nombre}"/></b></td>
        </tr>
        <tr>
            <th>Descripcion</th>
            <td><c:out value="${fiesta.descripcion}"/></td>
        </tr>
        <tr>
            <th>Precio</th>
            <td><c:out value="${fiesta.precio}"/></td>
        </tr>
        <tr>
            <th>Requisitos</th>
            <td><c:out value="${fiesta.requisitos}"/></td>
        </tr>
       <tr>
            <th>Fecha</th>
            <td><c:out value="${fiesta.fecha}"/></td>
        </tr>
        <tr>
            <th>Hora de inicio</th>
            <td><c:out value="${fiesta.horaInicio}"/></td>
        </tr>
        <tr>
            <th>Hora de fín</th>
            <td><c:out value="${fiesta.horaFin}"/></td>
        </tr>
        <tr>
            <th>Asistentes</th>
            <td><c:out value="${fiesta.numeroAsistentes}"/></td>
        </tr>
        <tr>
            <th>Imagen</th>
            <td><img src="${fiesta.imagen}" alt="${fiesta.imagen}"/></td>
        </tr>
        <tr>
            <th>Decisión</th>
            <td><c:out value="${fiesta.decision}"/></td>
        </tr>
        <tr>
            <th>Cliente</th>
            <td><c:out value="${fiesta.cliente}"/></td>
        </tr>
        <tr>
            <th>Local</th>
            <td><c:out value="${fiesta.local.direccion}"/></td>
        </tr>
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
            <th>Valoraci�n</th>
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
  
  <sec:authorize access="hasAuthority('propietario')">
	  <c:if test="${fiesta.local.propietario.id==userLoggedId}">
	    <button type="button" class="btn btn-default"
	     onclick="window.location.replace('/local/${fiesta.local.id}/fiesta/${fiesta.id}/aceptar')">Aceptar</button>
	    <button type="button" class="btn btn-default"
		onclick="window.location.replace('/local/${fiesta.local.id}fiesta/${fiesta.id}/denegar')">Denegar</button>
	</c:if>
  </sec:authorize>
  
    <sec:authorize access="hasAuthority('cliente')">
	  <c:if test="${fiesta.cliente.id==userLoggedId}">
	    <button type="button" class="btn btn-default"
	     onclick="window.location.replace('/fiestas/${fiesta.id}/editar')">Editar</button>	   
	</c:if>
  </sec:authorize>
  
  
</petclinic:layout>
