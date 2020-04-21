package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PropietarioController {
	private static final String VIEWS_PROPIETARIO_CREATE_OR_UPDATE_FORM = "propietarios/createOrUpdatePropietarioForm";
	
	private final PropietarioService			propietarioService;
	
	@Autowired
	public PropietarioController(final PropietarioService propietarioService
	) {	
		this.propietarioService = propietarioService;		
	}
	
	@InitBinder("propietario")
	public void initPetBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new PropietarioValidator());
	}
	
	@GetMapping(value = "/propietario/new")
	public String initCreationForm(Map<String, Object> model) {
		Propietario propietario = new Propietario();
		model.put("propietario", propietario);
		return VIEWS_PROPIETARIO_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/propietario/new")
	public String processCreationForm(@Valid Propietario propietario, BindingResult result,Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("propietario", propietario);
			return VIEWS_PROPIETARIO_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
			//creating owner, user and authorities
			this.propietarioService.save(propietario);			
			return "redirect:/propietario/locales";
			}catch(Exception e) {
				model.put("message", "Ya existe un usario con este nombre de usuario");
				return "exception";
			}
		}
	}
}
