<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="locales">

    <h2>Información del local</h2>


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
    </table>
</petclinic:layout>
