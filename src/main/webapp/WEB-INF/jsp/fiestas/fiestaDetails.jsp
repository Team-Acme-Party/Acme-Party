<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<petclinic:layout pageName="fiestaDetails">

	<h2>Información de la fiesta</h2>


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
			<th>Hora de fín</th>
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
			<th>Decisión</th>
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
		<c:if test="${fiesta.cliente.id ne userLoggedId and !esFiestaSolicitadaPorCliente}">
			<form onsubmit="return alerta(this)" method="get"
				action="/cliente/solicitudAsistencia/fiesta/${fiesta.id}">

				<input id="fiestaId" type="hidden" name="fiestaId"
					value="${fiesta.id}"> <input type="submit"
					class="btn btn-default" name="save" value="Solicitar asistencia" />

			</form>
		</c:if>
	</sec:authorize>


</petclinic:layout>

<script type="text/javascript">
	function alerta(form) {
		var opcion = confirm("�Desea solicitar la asistencia a esta fiesta?");
		if (opcion == true) {
			return true;
		} else {
			return false;
		}
	}
</script>
