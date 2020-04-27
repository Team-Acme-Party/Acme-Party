package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ClienteValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return Cliente.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Cliente cliente = (Cliente) target;

		if (cliente.getNombre() == "") {
			errors.rejectValue("nombre", "ValidateNotBlank");
		}

		if (cliente.getApellidos() == "") {
			errors.rejectValue("apellidos", "ValidateNotBlank");
		}

		if (cliente.getEmail() == "" || !cliente.getEmail().contains("@")) {
			errors.rejectValue("email", "ValidateEmail");
		}
		if (cliente.getTelefono() == "") {
			errors.rejectValue("telefono", "ValidateNotBlank");
		}
		if (cliente.getUser().getUsername() == "") {
			errors.rejectValue("user.username", "ValidateNotBlank");
		}
		if (cliente.getUser().getPassword() == "") {
			errors.rejectValue("user.password", "ValidateNotBlank");
		}
	}
}
