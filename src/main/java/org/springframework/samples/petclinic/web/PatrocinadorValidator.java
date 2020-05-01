package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PatrocinadorValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return Patrocinador.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Patrocinador patrocinador = (Patrocinador) target;

		if (patrocinador.getNombre() == "") {
			errors.rejectValue("nombre", "ValidateNotBlank");
		}
		if (!(patrocinador.getNombre().matches("[a-zA-Z ]*"))) {
			errors.rejectValue("nombre", "typeMismatch");
		}

		if (patrocinador.getApellidos() == "") {
			errors.rejectValue("apellidos", "ValidateNotBlank");
		}
		if (!(patrocinador.getApellidos().matches("[a-zA-Z ]*"))) {
			errors.rejectValue("apellidos", "typeMismatch");
		}

		if (!(patrocinador.getEmail().matches("[-\\w\\.]+@\\w+\\.\\w+"))) {
			errors.rejectValue("email", "ValidateEmail");
		}
		if (patrocinador.getTelefono() == "") {
			errors.rejectValue("telefono", "ValidateNotBlank");
		}
		if (!(patrocinador.getTelefono().matches("[0-9]{9}"))) {
			errors.rejectValue("telefono", "ValidatePhone");
		}

		if (patrocinador.getUser().getUsername() == "") {
			errors.rejectValue("user.username", "ValidateNotBlank");
		}
		if (patrocinador.getUser().getPassword() == "") {
			errors.rejectValue("user.password", "ValidateNotBlank");
		}

	}
}
