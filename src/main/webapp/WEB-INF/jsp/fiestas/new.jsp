<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="fiestas">
    <h2>
        Formulario de fiesta
    </h2>
    <form:form modelAttribute="fiesta" class="form-horizontal" action="/fiestas/new/${localId}">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:textField label="Descripcion" name="descripcion"/>
            <petclinic:inputField label="Precio" name="precio"/>
            <petclinic:textField label="Requisitos" name="requisitos"/>
            <petclinic:inputField label="Fecha" name="fecha"/>
            <petclinic:inputField label="Hora de inicio" name="horaInicio"/>
            <petclinic:inputField label="Hora de fin" name="horaFin"/>
            <petclinic:inputField label="Numero de asistentes" name="numeroAsistentes"/>
            <petclinic:inputField label="Imagen" name="imagen"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Registrar</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
