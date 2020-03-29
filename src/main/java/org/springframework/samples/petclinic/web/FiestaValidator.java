package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FiestaValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Fiesta.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Fiesta fiesta=(Fiesta)target;
		
		if(fiesta.getNumeroAsistentes()==null) {		
			errors.rejectValue("numeroAsistentes", "joe", "joe");
		}
		
	}
	
}
