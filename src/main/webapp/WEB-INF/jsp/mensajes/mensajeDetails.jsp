<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="mensajeDetails">

    <h2>Detalles del mensaje</h2>
    
    <table class="table table-striped">
        <tr>
            <th>Asunto</th>
            <td><b><c:out value="${mensaje.asunto}"/></b></td>
        </tr>
        <tr>
            <th>Cuerpo</th>
            <td><b><c:out value="${mensaje.cuerpo}"/></b></td>
        </tr>
        <tr>
            <th>Fecha</th>
            <td><b><c:out value="${mensaje.fecha}"/></b></td>
        </tr>
        <tr>
            <th>Hora</th>
            <td><b><c:out value="${mensaje.hora}"/></b></td>
        </tr>
        <tr>
            <th>Remitente</th>
            <td><b><c:out value="${mensaje.remitente}"/></b></td>
        </tr>
        <tr>
            <th>Destinatario</th>
            <td><b><c:out value="${mensaje.destinatario}"/></b></td>
        </tr>
       
     </table>   
</petclinic:layout>
