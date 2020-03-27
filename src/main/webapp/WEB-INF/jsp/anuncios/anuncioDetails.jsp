<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="anuncioDetails">

    <h2>Información del anuncio</h2>


    <table class="table table-striped">
        <tr>
            <th>Patrocinador</th>
            <td><b><c:out value="${anuncio.patrocinador}"/></b></td>
        </tr>
        <tr>
            <th>Fiesta</th>
            <td><c:out value="${anuncio.fiesta}"/></td>
        </tr>
        <tr>
            <th>Decision</th>
            <td><c:out value="${anuncio.decision}"/></td>
        </tr>

        <tr>
            <th>Imagen</th>
            <td><img src="${anuncio.imagen}" alt="${anuncio.imagen}"/></td>
        </tr>
        
     </table>   
</petclinic:layout>
