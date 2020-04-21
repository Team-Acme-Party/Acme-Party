
package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {

	private static final String		VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM	= "clientes/createOrUpdateClienteForm";

	private final ClienteService	clienteService;


	@Autowired
	public ClienteController(final ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@InitBinder("cliente")
	public void initPetBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new ClienteValidator());
	}

	@GetMapping(value = "/cliente/new")
	public String initCreationForm(final Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return ClienteController.VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/cliente/new")
	public String processCreationForm(@Valid final Cliente cliente, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("cliente", cliente);
			return ClienteController.VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				// creating owner, user and authorities
				this.clienteService.save(cliente);
				return "redirect:/cliente/fiestas";

			} catch (Exception e) {
				model.put("message", "Ya existe un usario con este nombre de usuario");
				return "exception";
			}
		}
	}
}
