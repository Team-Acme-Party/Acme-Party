<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="fiestas">
<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fecha").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
      <h2>
            <c:if test="${fiesta['new']}">Nueva </c:if> Fiesta
        </h2>
    <form:form modelAttribute="fiesta" class="form-horizontal">
        <div class="form-group has-feedback">
       <input type="hidden" name="Id" value="${fiesta.id}"/> 
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:textField label="Descripcion" name="descripcion"/>
            <petclinic:inputField label="Precio" name="precio"/>
            <petclinic:textField label="Requisitos" name="requisitos"/>
            <petclinic:inputField label="Fecha" name="fecha" />
            <petclinic:inputField label="Hora de inicio" name="horaInicio"/>
            <petclinic:inputField label="Hora de fin" name="horaFin"/>
            <petclinic:inputField label="Numero de asistentes" name="numeroAsistentes"/>
            <petclinic:inputField label="Imagen" name="imagen"/>
        </div>
        <div class="form-group">
             <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${fiesta['new']}">
                            <button class="btn btn-default" type="submit">Registrar fiesta</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Actualizar Fiesta</button>
                        </c:otherwise>
                    </c:choose>
                </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
