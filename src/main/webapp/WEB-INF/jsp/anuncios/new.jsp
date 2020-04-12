<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="anuncios">
    <jsp:body>
      <h2>
      	Registro de anuncio
      </h2>
      
      <c:choose>
      	<c:when test="${forLocal}">
      		<c:set var="url" value="/anuncio/new/${targetId}/local"/>
      	</c:when>
      	<c:otherwise>
      		<c:set var="url" value="/anuncio/new/${targetId}/fiesta"/>
      	</c:otherwise>
      </c:choose>
      
    <form:form modelAttribute="anuncio" class="form-horizontal" action="${url}">
        <div class="form-group has-feedback">
        	<petclinic:inputField label="Imagen" name="imagen"/>
        </div>
        <div class="form-group">
	        <div class="col-sm-offset-2 col-sm-10">
	        	<button class="btn btn-default" type="submit">Registrar anuncio</button>
	        </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
