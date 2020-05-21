
package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FiestaValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return Fiesta.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		Fiesta fiesta = (Fiesta) target;

		if (fiesta.getHoraFin() == null) {
			errors.rejectValue("horaFin", "ValidateHora");
		}

		if (fiesta.getHoraInicio() == null) {
			errors.rejectValue("horaInicio", "ValidateHora");
		}

		if (fiesta.getNombre() == "") {
			errors.rejectValue("nombre", "ValidateNotBlank");
		}

		if (fiesta.getImagen() == "") {
			errors.rejectValue("imagen", "ValidateNotBlankImagen");
		}

		if (fiesta.getDescripcion() == "") {
			errors.rejectValue("descripcion", "ValidateNotBlank");
		}

		if (fiesta.getRequisitos() == "") {
			errors.rejectValue("requisitos", "ValidateNotBlank");
		}

		if (fiesta.getPrecio() == null || fiesta.getPrecio() < 0) {
			errors.rejectValue("precio", "ValidatePrecio");
		}

		if (fiesta.getFecha() == null) {
			errors.rejectValue("fecha", "ValidateFecha");
		}

		if (fiesta.getAforo() == null || fiesta.getAforo() <= 0) {
			errors.rejectValue("aforo", "ValidateNumeroAsistentes");
		}

	}

}
