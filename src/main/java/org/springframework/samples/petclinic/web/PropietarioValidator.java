package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PropietarioValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return Propietario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Propietario propietario = (Propietario) target;

		if (propietario.getNombre() == "") {
			errors.rejectValue("nombre", "ValidateNotBlank");
		}
		if (!(propietario.getNombre().matches("[a-zA-Z ]*"))) {
			errors.rejectValue("nombre", "typeMismatch");
		}

		if (propietario.getApellidos() == "") {
			errors.rejectValue("apellidos", "ValidateNotBlank");
		}
		if (!(propietario.getApellidos().matches("[a-zA-Z ]*"))) {
			errors.rejectValue("apellidos", "typeMismatch");
		}

		if (!(propietario.getEmail().matches("[-\\w\\.]+@\\w+\\.\\w+"))) {
			errors.rejectValue("email", "ValidateEmail");
		}
		if (propietario.getTelefono() == "") {
			errors.rejectValue("telefono", "ValidateNotBlank");
		}		
		if (!(propietario.getTelefono().matches("[0-9]{9}"))) {
			errors.rejectValue("telefono", "ValidatePhone");
		}
		
		if (propietario.getUser().getUsername() == "") {
			errors.rejectValue("user.username", "ValidateNotBlank");
		}
		if (propietario.getUser().getPassword() == "") {
			errors.rejectValue("user.password", "ValidateNotBlank");
		}

	}
}
