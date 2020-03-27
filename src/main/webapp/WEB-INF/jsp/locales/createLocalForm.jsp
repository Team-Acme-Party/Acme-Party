<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="locales/listaLocales">
    <h2>
        Nuevo Local
    </h2>
    
    <form:form modelAttribute="local" class="form-horizontal" id="add-local-form">
        <div class="form-group has-feedback">
        
            <petclinic:inputField label="Direccion" name="direccion"/>
            <petclinic:inputField label="Capacidad" name="capacidad"/>
            <petclinic:inputField label="Condiciones" name="condiciones"/>
            <petclinic:inputField label="Imagen" name="imagen"/>
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Crear local</button>
            </div>
        </div>
    </form:form>
    
</petclinic:layout>
