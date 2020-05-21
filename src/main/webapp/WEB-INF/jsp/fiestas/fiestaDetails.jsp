<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<petclinic:layout pageName="fiestaDetails">

	<c:forEach items="${anuncios}" var="anuncio">
		<img src="${anuncio.imagen }" alt="${anuncio.imagen }" height="150"
			width="100%" />
	</c:forEach>
	<br>

	<h2>
		Informacion de la fiesta
		<c:if test="${fiesta.decision == 'ACEPTADO' }">
			<sec:authorize access="hasAuthority('patrocinador')">
				<a href="/anuncio/new/${fiesta.id}/fiesta" class="btn btn-default">Ofrecer
					anuncio</a>
			</sec:authorize>
		</c:if>
	</h2>


	<table class="table table-striped">
		<tr>
			<th>Nombre</th>
			<td><b><c:out value="${fiesta.nombre}" /></b></td>
		</tr>
		<tr>
			<th>Descripcion</th>
			<td><c:out value="${fiesta.descripcion}" /></td>
		</tr>
		<tr>
			<th>Precio</th>
			<td><c:out value="${fiesta.precio}" /></td>
		</tr>
		<tr>
			<th>Requisitos</th>
			<td><c:out value="${fiesta.requisitos}" /></td>
		</tr>
		<tr>
			<th>Fecha</th>
			<td><c:out value="${fiesta.fecha}" /></td>
		</tr>
		<tr>
			<th>Hora de inicio</th>
			<td><c:out value="${fiesta.horaInicio}" /></td>
		</tr>
		<tr>
			<th>Hora de fin</th>
			<td><c:out value="${fiesta.horaFin}" /></td>
		</tr>
		<tr>
			<th>Asistentes</th>
			<td><c:out value="${fiesta.numeroAsistentes}" /></td>
		</tr>
		<tr>
			<th>Imagen</th>
			<td><img src="${fiesta.imagen}" alt="${fiesta.imagen}" /></td>
		</tr>
		<tr>
			<th>Decision</th>
			<td><c:out value="${fiesta.decision}" /></td>
		</tr>
		<tr>
			<th>Cliente</th>
			<td><c:out value="${fiesta.cliente}" /></td>
		</tr>
		<tr>
			<th>Local</th>
			<td><c:out value="${fiesta.local.direccion}" /></td>
		</tr>
	</table>

	<sec:authorize access="hasAuthority('propietario')">
		<c:if
			test="${fiesta.local.propietario.id==userLoggedId && fiesta.decision=='PENDIENTE'}">
			<button type="button" class="btn btn-default"
				onclick="window.location.replace('/local/${fiesta.local.id}/fiesta/${fiesta.id}/aceptar')">Aceptar</button>
			<button type="button" class="btn btn-default"
				onclick="window.location.replace('/local/${fiesta.local.id}/fiesta/${fiesta.id}/denegar')">Denegar</button>
		</c:if>
	</sec:authorize>

	<sec:authorize access="hasAuthority('cliente')">
		<c:if test="${fiesta.cliente.id==userLoggedId}">
			<button type="button" class="btn btn-default"
				onclick="window.location.replace('/fiestas/${fiesta.id}/editar')">Editar</button>
		</c:if>


		<c:if test="${fiesta.cliente.id ne userLoggedId}">
			<c:if test="${!esFiestaSolicitadaPorCliente}">
				<c:if test="${(fiesta.aforo > fiesta.numeroAsistentes)}">

					<form onsubmit="return alerta(this)" method="get"
						action="/cliente/solicitudAsistencia/fiesta/${fiesta.id}">
						<input id="fiestaId" type="hidden" name="fiestaId"
							value="${fiesta.id}"> <input type="submit"
							class="btn btn-default" name="save" value="Solicitar asistencia" />
					</form>

				</c:if>
				<c:if test="${!(fiesta.aforo > fiesta.numeroAsistentes)}">
					<c:out value="Aforo completo" />
				</c:if>
			</c:if>
			<c:if test="${esFiestaSolicitadaPorCliente}">
				<c:out value="Ya has solicitado asistencia a esta fiesta" />
			</c:if>
		</c:if>
		<hr />

	</sec:authorize>

	<h5>Comentarios</h5>
	<table id="comentariosTable" class="table table-striped">
		<thead>
			<tr>
				<th>Cliente</th>
				<th>Cuerpo</th>
				<th>Fecha</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${comentarios}" var="comentario">
				<tr>
					<td><c:out value="${comentario.cliente}" /></td>
					<td><c:out value="${comentario.cuerpo}" /></td>
					<td><c:out value="${comentario.fecha}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<sec:authorize access="hasAuthority('cliente')">
		<c:if test="${clienteFiesta}">
			<h2>Comentar fiesta</h2>

			<form:form id="form" modelAttribute="comentario"
				class="form-horizontal" action="/comentario/new/fiesta/${fiestaId}">
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
				$(document).ready(function() {
					$("#form").submit(function() {
						var cuerpo = $("#cuerpo").val();
						debugger;
						if (cuerpo == "") {							
							alert("El comentario no puede estar vacio");
							return false;
						}
						return true;
					});
				});
			</script>
		</c:if>
	</sec:authorize>

	<h5>Valoraciones</h5>
	<table id="valoracionesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Comentario</th>
				<th>Valoracion</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${valoraciones}" var="valoracion">
				<tr>
					<td><c:out value="${valoracion.comentario}" /></td>
					<td><c:out value="${valoracion.valor}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<sec:authorize access="hasAuthority('cliente')">
		<c:if test="${clienteValoracion}">
			<h2>Valorar fiesta</h2>

			<form:form id="formValoracion" modelAttribute="valoracion"
				class="form-horizontal" action="/valoracion/new/fiesta/${fiestaId}">
				<div class="form-group has-feedback">

					<petclinic:inputField label="Comentario" name="comentario" />
					<petclinic:inputField label="Valor" name="valor" />

				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button class="btn btn-default" type="submit">Enviar</button>
					</div>
				</div>
			</form:form>
			<script>
				$(document).ready(function() {
					$("#formValoracion").submit(function() {
						debugger;
						var valor = $("#valor").val();
						debugger;
						if (valor <0 || valor>5 ||valor=="") {
							alert("El valor debe de ser entre 0 y 5");
							return false;
						}
						return true;
					});
				});
			</script>
		</c:if>
	</sec:authorize>

	<sec:authorize access="hasAuthority('cliente')">
		<c:if test="${fiesta.cliente.id==userLoggedId}">
			<h2>Solicitudes de asistencia</h2>
		</c:if>
	</sec:authorize>



	<sec:authorize access="hasAuthority('cliente')">
		<c:if test="${fiesta.cliente.id==userLoggedId}">

			<table id="solicitudesTable" class="table table-striped">
				<thead>
					<tr>
						<th>Cliente</th>
						<th>Decision</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${solicitudes}" var="solicitud">
						<tr>
							<td><c:out value="${solicitud.cliente.nombre}" /></td>
							<td><c:out value="${solicitud.decision}" /></td>
							<td><c:if test="${solicitud.decision eq 'PENDIENTE'}">
									<button type="button" class="btn btn-default"
										onclick="window.location.replace('/cliente/solicitudAsistencia/aceptar/${solicitud.id}')">Aceptar</button>
								</c:if></td>
							<td><c:if test="${solicitud.decision eq 'PENDIENTE'}">
									<button type="button" class="btn btn-default"
										onclick="window.location.replace('/cliente/solicitudAsistencia/rechazar/${solicitud.id}')">Rechazar</button>
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</c:if>
	</sec:authorize>


</petclinic:layout>

<script type="text/javascript">
	function alerta(form) {
		var opcion = confirm("¿Desea solicitar la asistencia a esta fiesta?");
		if (opcion == true) {
			return true;
		} else {
			return false;
		}
	}
</script>
