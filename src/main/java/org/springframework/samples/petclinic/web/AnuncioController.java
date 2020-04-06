
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PatrocinadorService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnuncioController {

	private final AnuncioService		anuncioService;
	private final PatrocinadorService	patrocinadorService;
	private final ClienteService		clienteService;
	private final PropietarioService	propietarioService;
	private final LocalService			localService;
	private final FiestaService			fiestaService;


	@Autowired
	public AnuncioController(final AnuncioService anuncioService, final PatrocinadorService patrocinadorService, final LocalService localService, final FiestaService fiestaService, final ClienteService clienteService,
		final PropietarioService propietarioService) {
		this.anuncioService = anuncioService;
		this.patrocinadorService = patrocinadorService;
		this.localService = localService;
		this.fiestaService = fiestaService;
		this.clienteService = clienteService;
		this.propietarioService = propietarioService;
	}

	@GetMapping(value = {
		"/anuncios/{anuncioId}"
	})
	public ModelAndView showAnuncio(@PathVariable("anuncioId") final int anuncioId) {
		ModelAndView mav = new ModelAndView("anuncios/anuncioDetails");
		mav.addObject(this.anuncioService.findById(anuncioId));
		return mav;
	}

	@GetMapping(value = {
		"/patrocinador/anuncios"
	})
	public String verMisAnunciosPatrocinador(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Patrocinador p = this.patrocinadorService.findByUsername(username);
		Collection<Anuncio> anuncios = this.anuncioService.findByPatrocinadorId(p.getId());
		Collection<Anuncio> paraFiestas = new ArrayList<>();
		Collection<Anuncio> paraLocales = new ArrayList<>();
		for (Anuncio a : anuncios) {
			if (a.getLocal() != null) {
				paraLocales.add(a);
			} else {
				paraFiestas.add(a);
			}
		}

		model.put("paraFiestas", paraFiestas);
		model.put("paraLocales", paraLocales);
		model.put("isPatrocinador", true);

		return "anuncios/listaAnuncios";
	}

	@GetMapping(value = {
		"/cliente/anuncios"
	})
	public String verMisAnunciosCliente(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Cliente c = this.clienteService.findByUsername(username);
		Collection<Anuncio> anuncios = this.anuncioService.findByClienteId(c.getId());
		model.put("anuncios", anuncios);
		model.put("isCliente", true);

		return "anuncios/listaAnuncios";
	}

	@GetMapping(value = {
		"/propietario/anuncios"
	})
	public String verMisAnunciosPropietario(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Propietario p = this.propietarioService.findByUsername(username);
		Collection<Anuncio> anuncios = this.anuncioService.findByPropietarioId(p.getId());
		model.put("anuncios", anuncios);
		model.put("isPropietario", true);

		return "anuncios/listaAnuncios";
	}

	//Crear para un local
	@GetMapping(value = "/anuncio/new/{targetId}/local")
	public String initCreationFormLocal(@PathVariable("targetId") final int targetId, final ModelMap model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Patrocinador patrocinador = this.patrocinadorService.findByUsername(username);
			if (patrocinador == null) {
				throw new Exception();
			} else {
				Anuncio anuncio = new Anuncio();
				model.put("anuncio", anuncio);
				model.put("targetId", targetId);
				model.put("forLocal", true);
				return "anuncios/new";
			}
		} catch (Exception e) {
			model.put("exception", "No tienes acceso para crear un anuncio");
			return "exception";
		}
	}

	@PostMapping(value = "/anuncio/new/{targetId}/local")
	public String processCreationFormLocal(@PathVariable("targetId") final int targetId, @Valid final Anuncio anuncio, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("anuncio", anuncio);
			model.put("forLocal", true);
			return "anuncios/new";
		} else {
			Patrocinador patrocinador = this.patrocinadorService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			Local local = this.localService.findLocalById(targetId);
			anuncio.setLocal(local);
			anuncio.setPatrocinador(patrocinador);
			anuncio.setDecision("PENDIENTE");
			this.anuncioService.save(anuncio);
			return "redirect:/patrocinador/anuncios";
		}
	}

	//Crear para una fiesta
	@GetMapping(value = "/anuncio/new/{targetId}/fiesta")
	public String initCreationFormFiesta(@PathVariable("targetId") final int targetId, final ModelMap model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Patrocinador patrocinador = this.patrocinadorService.findByUsername(username);
			if (patrocinador == null) {
				throw new Exception();
			} else {
				Anuncio anuncio = new Anuncio();
				model.put("anuncio", anuncio);
				model.put("targetId", targetId);
				return "anuncios/new";
			}
		} catch (Exception e) {
			model.put("exception", "No tienes acceso para crear un anuncio");
			return "exception";
		}
	}

	@PostMapping(value = "/anuncio/new/{targetId}/fiesta")
	public String processCreationFormFiesta(@PathVariable("targetId") final int targetId, @Valid final Anuncio anuncio, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("anuncio", anuncio);
			return "anuncios/new";
		} else {
			Patrocinador patrocinador = this.patrocinadorService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			Fiesta fiesta = this.fiestaService.findFiestaById(targetId);
			anuncio.setFiesta(fiesta);
			anuncio.setPatrocinador(patrocinador);
			anuncio.setDecision("PENDIENTE");
			this.anuncioService.save(anuncio);
			return "redirect:/patrocinador/anuncios";
		}
	}

	@GetMapping(value = {
		"/propietario/anuncio/{anuncioId}/aceptar"
	})
	public String aceptarSolicitudPropietario(@PathVariable("anuncioId") final int anuncioId, final Map<String, Object> model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Propietario propietario = this.propietarioService.findByUsername(username);
			Anuncio a = this.anuncioService.findById(anuncioId);

			if (propietario == null || !this.anuncioService.findByPropietarioId(propietario.getId()).contains(a)) {
				throw new Exception();
			} else {
				this.anuncioService.aceptar(a);
				return "redirect:/propietario/anuncios";
			}
		} catch (Exception e) {
			model.put("message", "No tienes acceso para aceptar una anuncio en este local");
			return "exception";
		}
	}

	@GetMapping(value = {
		"/propietario/anuncio/{anuncioId}/rechazar"
	})
	public String rechazarSolicitudPropietario(@PathVariable("anuncioId") final int anuncioId, final Map<String, Object> model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Propietario propietario = this.propietarioService.findByUsername(username);
			Anuncio a = this.anuncioService.findById(anuncioId);

			if (propietario == null || !this.anuncioService.findByPropietarioId(propietario.getId()).contains(a)) {
				throw new Exception();
			} else {
				this.anuncioService.rechazar(a);
				return "redirect:/propietario/anuncios";
			}
		} catch (Exception e) {
			model.put("message", "No tienes acceso para rechazar una anuncio en este local");
			return "exception";
		}
	}

	@GetMapping(value = {
		"/cliente/anuncio/{anuncioId}/aceptar"
	})
	public String aceptarSolicitudCliente(@PathVariable("anuncioId") final int anuncioId, final Map<String, Object> model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Cliente cliente = this.clienteService.findByUsername(username);
			Anuncio a = this.anuncioService.findById(anuncioId);

			if (cliente == null || !this.anuncioService.findByClienteId(cliente.getId()).contains(a)) {
				throw new Exception();
			} else {
				this.anuncioService.aceptar(a);
				return "redirect:/cliente/anuncios";
			}
		} catch (Exception e) {
			model.put("message", "No tienes acceso para aceptar un anuncio en esta fiesta");
			return "exception";
		}
	}

	@GetMapping(value = {
		"/cliente/anuncio/{anuncioId}/rechazar"
	})
	public String rechazarSolicitudCliente(@PathVariable("anuncioId") final int anuncioId, final Map<String, Object> model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Cliente cliente = this.clienteService.findByUsername(username);
			Anuncio a = this.anuncioService.findById(anuncioId);

			if (cliente == null || !this.anuncioService.findByClienteId(cliente.getId()).contains(a)) {
				throw new Exception();
			} else {
				this.anuncioService.rechazar(a);
				return "redirect:/cliente/anuncios";
			}
		} catch (Exception e) {
			model.put("message", "No tienes acceso para rechazar un anuncio en esta fiesta");
			return "exception";
		}
	}

}
