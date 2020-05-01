<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="propietarios">
    <h2>
        <c:if test="${propietario['new']}">Nuevo </c:if> Propietario
    </h2>
    <form:form modelAttribute="propietario" class="form-horizontal" id="add-propietario-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Apellidos" name="apellidos"/>
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="Teléfono" name="telefono"/>
            <petclinic:inputField label="Foto" name="foto"/>
            <petclinic:inputField label="Usuario" name="user.username"/>
            <petclinic:inputFieldTypePassword label="Contraseña" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${propietario['new']}">
                        <button class="btn btn-default" type="submit">Nuevo Popietario</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Propietario</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>