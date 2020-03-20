<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="fiestaDetails">

    <h2>Información de la fiesta</h2>


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
</petclinic:layout>
