
package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.service.PatrocinadorService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PatrocinadorController {

	private static final String			VIEWS_PATROCINADOR_CREATE_OR_UPDATE_FORM	= "patrocinadores/createOrUpdatePatrocinadorForm";

	private final PatrocinadorService	patrocinadorService;


	@Autowired
	public PatrocinadorController(final PatrocinadorService patrocinadorService) {
		this.patrocinadorService = patrocinadorService;
	}

	@InitBinder("patrocinador")
	public void initPetBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new PatrocinadorValidator());
	}

	@GetMapping(value = "/patrocinador/new")
	public String initCreationForm(final Map<String, Object> model) {
		Patrocinador patrocinador = new Patrocinador();
		model.put("patrocinador", patrocinador);
		return PatrocinadorController.VIEWS_PATROCINADOR_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/patrocinador/new")
	public String processCreationForm(@Valid final Patrocinador patrocinador, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("patrocinador", patrocinador);
			return PatrocinadorController.VIEWS_PATROCINADOR_CREATE_OR_UPDATE_FORM;
		} else {
			// creating owner, user and authorities
			try {
				this.patrocinadorService.save(patrocinador);
				return "redirect:/patrocinador/anuncios";
			} catch (Exception e) {

				model.put("message", "Ya existe un usario con este nombre de usuario");
				return "exception";

			}

		}
	}
}
