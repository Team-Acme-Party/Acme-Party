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
		Propietario propietario=(Propietario)target;
		
		if (propietario.getNombre() == "") {
			errors.rejectValue("nombre", "ValidateNotBlank");
		}

		if (propietario.getApellidos() == "") {
			errors.rejectValue("apellidos", "ValidateNotBlank");
		}

		if (propietario.getEmail() == "" || !propietario.getEmail().contains("@")) {
			errors.rejectValue("email", "ValidateEmail");
		}
		if (propietario.getTelefono() == "") {
			errors.rejectValue("telefono", "ValidateNotBlank");
		}
		if (propietario.getUser().getUsername() == "") {
			errors.rejectValue("user.username", "ValidateNotBlank");
		}
		if (propietario.getUser().getPassword() == "") {
			errors.rejectValue("user.password", "ValidateNotBlank");
		}
		
	}
}
