<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="misanuncios">
    <h2>Anuncios</h2>

    <table id="anunciosTable" class="table table-striped">
        <thead>
        <tr>
            <th>Local</th>
            <th>Fiesta</th>
            <th>Decision</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${anuncios}" var="anuncios">
            <tr>
                <td>
                    <c:out value="${anuncios.local}"/>
                </td>
                <td>
                    <c:out value="${anuncios.fiesta}"/>
                </td>
                <td>
                    <c:out value="${anuncios.decision}"/>
                </td>
                <td>
	                    <button type="button" class="btn btn-default" onclick="window.location.replace('/anuncio/${anuncios.id}')">Ver detalles</button>
	            </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
     
</petclinic:layout>
