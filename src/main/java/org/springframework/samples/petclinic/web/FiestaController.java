
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FiestaController {

	private final FiestaService		fiestaService;
	private final LocalService		localService;
	private final ClienteService	clienteService;


	@Autowired
	public FiestaController(final FiestaService fiestaService, final LocalService localService, final ClienteService clienteService) {
		this.fiestaService = fiestaService;
		this.localService = localService;
		this.clienteService = clienteService;
	}

	@GetMapping(value = {
		"/fiestas/{fiestaId}"
	})
	public ModelAndView showFiesta(@PathVariable("fiestaId") final int fiestaId) {
		ModelAndView mav = new ModelAndView("fiestas/fiestaDetails");
		mav.addObject(this.fiestaService.findFiestaById(fiestaId));
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

	@GetMapping(value = "/fiestas/new/{localId}")
	public String initCreationForm(@PathVariable("localId") final int localId, final ModelMap model) {
		Fiesta fiesta = new Fiesta();
		model.put("fiesta", fiesta);
		model.put("localId", localId);
		return "fiestas/new";
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


	@GetMapping(value = {
		"/cliente/asistencias"
	})
	public String verMisAsistencias(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Cliente c = this.clienteService.findByUsername(username);
		Collection<Fiesta> fiestas = this.fiestaService.findAsistenciasByClienteId(c.getId());
		model.put("fiestas", fiestas);
		model.put("misasistencias", true);

		return "fiestas/listaFiestas";
	}

}
