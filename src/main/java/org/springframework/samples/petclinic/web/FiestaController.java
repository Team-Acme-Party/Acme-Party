
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;;

@Controller
public class FiestaController {

	private final FiestaService			fiestaService;
	private final LocalService			localService;
	private final ClienteService		clienteService;
	private final PropietarioService	propietarioService;


	@Autowired
	public FiestaController(final FiestaService fiestaService, final LocalService localService, final ClienteService clienteService, final PropietarioService propietarioService) {
		this.fiestaService = fiestaService;
		this.localService = localService;
		this.clienteService = clienteService;
		this.propietarioService = propietarioService;
	}

	@InitBinder("fiesta")
	public void initPetBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new FiestaValidator());
	}

	@GetMapping(value = {
		"/fiestas/{fiestaId}"
	})
	public ModelAndView showFiesta(@PathVariable("fiestaId") final int fiestaId) {
		ModelAndView mav = new ModelAndView("fiestas/fiestaDetails");

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Propietario p = this.propietarioService.findByUsername(username);
		Cliente cliente = this.clienteService.findByUsername(username);

		if (p != null) {
			mav.addObject("userLoggedId", p.getId());
		} else if (cliente != null) {
			mav.addObject("userLoggedId", cliente.getId());
		}

		mav.addObject("fiesta", this.fiestaService.findFiestaById(fiestaId));
		return mav;
	}

	@GetMapping(value = {
		"/fiestas/buscar"
	})
	public String formularioBuscarLocales(final Map<String, Object> model) {

		Fiesta f = new Fiesta();
		model.put("fiesta", f);

		return "fiestas/buscarFiestas";
	}

	@GetMapping(value = "/fiestas")
	public String buscarLocales(final Fiesta f, final BindingResult result, final Map<String, Object> model) {

		if (f.getNombre() == null) {
			f.setNombre("");
		}

		Collection<Fiesta> results = this.fiestaService.findByNombre(f.getNombre());
		if (results.isEmpty()) {
			result.rejectValue("nombre", "notFound", "not found");
			return "fiestas/buscarFiestas";
		} else {
			model.put("fiestas", results);
			return "fiestas/listaFiestas";
		}
	}

	@GetMapping(value = {
		"/cliente/fiestas"
	})
	public String verMisFiestas(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Cliente c = this.clienteService.findByUsername(username);
		Collection<Fiesta> fiestas = this.fiestaService.findByClienteId(c.getId());
		model.put("fiestas", fiestas);
		model.put("misfiestas", true);

		return "fiestas/listaFiestas";
	}

	// CREATE
	@GetMapping(value = "/fiestas/new/{localId}")
	public String initCreationForm(@PathVariable("localId") final int localId, final ModelMap model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Cliente cliente = this.clienteService.findByUsername(username);
			if (cliente == null) {
				throw new Exception();
			} else {
				Fiesta fiesta = new Fiesta();
				model.put("fiesta", fiesta);
				model.put("localId", localId);
				return "fiestas/new";
			}
		} catch (Exception e) {
			model.put("exception", "No tienes acceso para crear una fiesta");
			return "exception";
		}
	}

	@PostMapping(value = "/fiestas/new/{localId}")
	public String processCreationForm(@PathVariable("localId") final int localId, @Valid final Fiesta fiesta, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("fiesta", fiesta);
			return "fiestas/new";
		} else {
			Cliente cliente = this.clienteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			Local local = this.localService.findLocalById(localId);
			fiesta.setLocal(local);
			fiesta.setCliente(cliente);
			fiesta.setDecision("PENDIENTE");
			this.fiestaService.save(fiesta);
			return "redirect:/fiestas/" + fiesta.getId();
		}
	}

	// EDIT
	@GetMapping(value = "/fiestas/{fiestaId}/editar")
	public String initUpdateForm(@PathVariable("fiestaId") final int fiestaId, final ModelMap model) {
		Fiesta fiesta = this.fiestaService.findFiestaById(fiestaId);

		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Cliente cliente = this.clienteService.findByUsername(username);
			if (cliente == null) {
				throw new Exception();
			} else {
				model.put("fiesta", fiesta);
				return "fiestas/new";
			}
		} catch (Exception e) {
			model.put("message", "No tienes acceso para editar una fiesta");
			return "exception";
		}
	}

	@PostMapping(value = {
		"/fiestas/{fiestaId}/editar"
	})
	public String processUpdateForm(@Valid final Fiesta fiesta, final BindingResult result, @PathVariable("fiestaId") final int fiestaId, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("fiesta", fiesta);
			return "fiestas/new";
		} else {
			Fiesta fiestaToUpdate = this.fiestaService.findFiestaById(fiestaId);
			BeanUtils.copyProperties(fiesta, fiestaToUpdate, "decision", "cliente", "local");
			try {
				this.fiestaService.save(fiestaToUpdate);
			} catch (Exception e) {
				result.rejectValue("name", "duplicate", "already exists");
				return "fiestas/new";
			}

			return "redirect:/fiestas/" + fiesta.getId();
		}
	}

}
