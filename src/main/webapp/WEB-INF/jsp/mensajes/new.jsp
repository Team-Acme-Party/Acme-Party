
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute="mensaje" class="form-horizontal"
	id="create-mensaje-form">
	<div class="form-group has-feedback">
		<petclinic:inputField label="Asunto" name="asunto" />
		<petclinic:inputField label="Cuerpo" name="cuerpo" />
		<petclinic:selectField label="Destinatario" name="destinatario" size="usuarios.size" names="${usuarios}"></petclinic:selectField> 
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<button class="btn btn-default" type="submit">Enviar mensaje</button>

		</div>
	</div>
</form:form>