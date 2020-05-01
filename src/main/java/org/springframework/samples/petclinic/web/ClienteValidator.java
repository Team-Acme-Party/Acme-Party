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
		if (!(cliente.getNombre().matches("[a-zA-Z ]*"))) {
			errors.rejectValue("nombre", "typeMismatch");
		}

		if (cliente.getApellidos() == "") {
			errors.rejectValue("apellidos", "ValidateNotBlank");
		}
		if (!(cliente.getApellidos().matches("[a-zA-Z ]*"))) {
			errors.rejectValue("apellidos", "typeMismatch");
		}

		if (!(cliente.getEmail().matches("[-\\w\\.]+@\\w+\\.\\w+"))) {
			errors.rejectValue("email", "ValidateEmail");
		}
		if (cliente.getTelefono() == "") {
			errors.rejectValue("telefono", "ValidateNotBlank");
		}
		if (!(cliente.getTelefono().matches("[0-9]{9}"))) {
			errors.rejectValue("telefono", "ValidatePhone");
		}
		
		if (cliente.getUser().getUsername() == "") {
			errors.rejectValue("user.username", "ValidateNotBlank");
		}
		if (cliente.getUser().getPassword() == "") {
			errors.rejectValue("user.password", "ValidateNotBlank");
		}
	}
}
