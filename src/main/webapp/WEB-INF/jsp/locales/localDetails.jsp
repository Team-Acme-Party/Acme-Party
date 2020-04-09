<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<petclinic:layout pageName="locales">


	<c:forEach items="${anuncios}" var="anuncio">
		<img src="${anuncio.imagen }" alt="${anuncio.imagen }" height="150"
			width="100%" />
	</c:forEach>
	<br>

	<h2>
		Informacion del local
		<c:if test="${local.decision == 'ACEPTADO' }">
			<sec:authorize access="hasAuthority('cliente')">
				<a href="/fiestas/new/${local.id}" class="btn btn-default">Solicitar
					fiesta</a>
			</sec:authorize>
			<sec:authorize access="hasAuthority('patrocinador')">
				<a href="/anuncio/new/${local.id}/local" class="btn btn-default">Ofrecer
					anuncio</a>
			</sec:authorize>
		</c:if>
	</h2>


	<table class="table table-striped ">
		<tr>
			<th>Dirección</th>
			<td><b><c:out value="${local.direccion}" /></b></td>
		</tr>
		<tr>
			<th>Capacidad</th>
			<td><c:out value="${local.capacidad}" /></td>
		</tr>
		<tr>
			<th>Condiciones</th>
			<td><c:out value="${local.condiciones}" /></td>
		</tr>
		<tr>
			<th>Imagen</th>
			<td><img src="${local.imagen}" alt="${local.imagen}" /></td>
		</tr>
		<tr>
			<th>Decisión</th>
			<td><c:out value="${local.decision}" /></td>
		</tr>
		<tr>
			<th>Propietario</th>
			<td><c:out value="${local.propietario}" /></td>
		</tr>
		<sec:authorize access="hasAuthority('admin')">
			<c:if test="${local.decision == 'PENDIENTE'}">
				<tr>
					<td>
						<button type="button" class="btn btn-default"
							onclick="window.location.replace('/administrador/local/${local.id}/aceptar')">ACEPTAR</button>
						<button type="button" class="btn btn-default"
							onclick="window.location.replace('/administrador/local/${local.id}/rechazar')">RECHAZAR</button>
					</td>
				</tr>
			</c:if>
			</sec:authorize>

			
	</table>
	

	
	<sec:authorize access="hasAuthority('cliente')">
		<c:if test="${clienteLocal}">
			<h2>Comentar local</h2>
			
			<form:form id="form" modelAttribute="comentario" class="form-horizontal" action="/comentario/new/local/${localId}">
				<div class="form-group has-feedback">

					<petclinic:inputField label="Cuerpo" name="cuerpo" />


				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button class="btn btn-default" type="submit">Enviar</button>
					</div>
				</div>
			</form:form>
	<script>
	$(document).ready(function(){
		$("#form").submit(function(){
			var cuerpo = $("#cuerpo").val();
			alert(cuerpo);
			if(cuerpo==""){
				alert("El comentario no puede estar vacio");
				return false;
			}
			return true;
		});
	});
	
	</script>
		</c:if>
	</sec:authorize>



</petclinic:layout>
