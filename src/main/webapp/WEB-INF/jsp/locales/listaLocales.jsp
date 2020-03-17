<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="locales">
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
</petclinic:layout>
