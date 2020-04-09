
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.SolicitudAsistenciaService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LocalController {

	private static final String VIEWS_CAUSE_CREATE_FORM = "locales/createLocalForm";
	private final LocalService localService;
	private final PropietarioService propietarioService;
	private final FiestaService fiestaService;
	private final AnuncioService anuncioService;
	private final ClienteService clienteService;
	private final SolicitudAsistenciaService solicitudAsistenciaService;

	@Autowired
	public LocalController(final LocalService localService, final PropietarioService propietarioService,
			final FiestaService fiestaService, final ClienteService clienteService, final AnuncioService anuncioService
			,final SolicitudAsistenciaService solicitudAsistenciaService) {
		this.localService = localService;
		this.propietarioService = propietarioService;
		this.fiestaService = fiestaService;
		this.clienteService= clienteService;
		this.anuncioService = anuncioService;
		this.solicitudAsistenciaService = solicitudAsistenciaService;
	}

	@GetMapping(value = { "/locales/{localId}" })
	public ModelAndView showLocal(@PathVariable("localId") final int localId) {
		
		ModelAndView mav = new ModelAndView("locales/localDetails");
		Collection<Anuncio> anuncios = this.anuncioService.findByLocalId(localId);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Cliente c = this.clienteService.findByUsername(username);
		if(c!=null) {
			mav.addObject("cliente",c);
			Collection<Fiesta> fiestasCliente = solicitudAsistenciaService.findSolicitudFiestaByClienteId(c.getId());
			if(fiestasCliente.stream().anyMatch(a->a.getLocal().equals(localService.findLocalById(localId)))){
				mav.addObject("clienteLocal",true);
				Comentario comentario = new Comentario();
				mav.addObject("comentario", comentario);
				
			}
		}
		mav.addObject(this.localService.findLocalById(localId));
		mav.addObject("anuncios", anuncios);
		return mav;}


	@GetMapping(value = "/locales/buscar")
	public String formularioBuscarLocales(final Map<String, Object> model) {

		Local local = new Local();
		model.put("local", local);

		return "locales/buscarLocales";
	}

	@GetMapping(value = "/locales")
	public String buscarLocales(final Local local, final BindingResult result, final Map<String, Object> model) {

		if (local.getDireccion() == null) {
			local.setDireccion("");
		}

		// find owners by last name
		Collection<Local> results = this.localService.findByDireccion(local.getDireccion());
		if (results.isEmpty()) {
			result.rejectValue("direccion", "notFound", "not found");
			return "locales/buscarLocales";
		}
		// else if (results.size() == 1) {
		// Local res = results.iterator().next();
		// return "redirect:/locales/" + res.getId();}
		else {
			model.put("locales", results);
			return "locales/listaLocales";
		}
	}

	@GetMapping(value = {
		"/propietario/locales"
	})
	public String verMisLocales(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Propietario p = this.propietarioService.findByUsername(username);
		Collection<Local> locales = this.localService.findByPropietarioId(p.getId());
		model.put("locales", locales);
		model.put("mislocales", true);

		return "locales/listaLocales";
	}

	@GetMapping(value = {
		"/local/{localId}/fiestas"
	})
	public String verSolicitudes(@PathVariable("localId") final int localId, final Map<String, Object> model) {

		Collection<Fiesta> fiestas = this.fiestaService.findFiestasPendientesByLocalId(localId);

		model.put("fiestas", fiestas);
		return "fiestas/listaFiestas";
	}

	@GetMapping(value = {
		"/local/{localId}/fiesta/{fiestaId}/aceptar"
	})
	public String aceptarSolicitud(@PathVariable("fiestaId") final int fiestaId, @PathVariable("localId") final int localId, final Map<String, Object> model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Propietario propietario = this.propietarioService.findByUsername(username);

			if (propietario == null || this.localService.findLocalById(localId).getPropietario() != propietario) {
				throw new Exception();
			} else {
				this.fiestaService.aceptarSolicitud(fiestaId);
				Collection<Fiesta> fiestas = this.fiestaService.findAccepted();
				Collection<Local> locales = this.localService.findAccepted();
				model.put("fiestas", fiestas);
				model.put("locales", locales);
				return "welcome";
			}
		} catch (Exception e) {
			model.put("message", "No tienes acceso para aceptar una fiesta en este local");
			return "exception";
		}
	}

	@GetMapping(value = {
		"/local/{localId}/fiesta/{fiestaId}/denegar"
	})
	public String denegarSolicitud(@PathVariable("fiestaId") final int fiestaId, @PathVariable("localId") final int localId, final Map<String, Object> model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Propietario propietario = this.propietarioService.findByUsername(username);

			if (propietario == null || this.localService.findLocalById(localId).getPropietario() != propietario) {
				throw new Exception();
			} else {
				this.fiestaService.denegarSolicitud(fiestaId);
				Collection<Fiesta> fiestas = this.fiestaService.findAccepted();
				Collection<Local> locales = this.localService.findAccepted();
				model.put("fiestas", fiestas);
				model.put("locales", locales);
				return "welcome";
			}
		} catch (Exception e) {
			model.put("message", "No tienes acceso para aceptar una fiesta en este local");
			return "exception";
		}
	}

	@GetMapping(value = {
		"administrador/locales"
	})
	public String todosLosLocales(final Map<String, Object> model) {

		Collection<Local> locales = this.localService.findPending();
		model.put("locales", locales);
		model.put("mislocales", true);

		return "locales/listaLocales";
	}

	@GetMapping(value = {
		"/administrador/local/{localId}/rechazar"
	})
	public String denegarSolicitudLocal(@PathVariable("localId") final int localId, final Map<String, Object> model) {
		Local local = this.localService.denegarSolicitudLocal(localId);

		model.put("local", local);
		return "redirect:/administrador/locales";
	}

	@GetMapping(value = {
		"/administrador/local/{localId}/aceptar"
	})
	public String aceptarSolicitudLocal(@PathVariable("localId") final int localId, final Map<String, Object> model) {
		Local local = this.localService.aceptarSolicitudLocal(localId);

		model.put("local", local);
		return "redirect:/administrador/locales";
	}

	@GetMapping(value = {
		"/locales/new"
	})
	public String initCreationForm(final Map<String, Object> model) {
		Local local = new Local();
		model.put("local", local);
		return LocalController.VIEWS_CAUSE_CREATE_FORM;
	}

	@PostMapping(value = {
		"/locales/new"
	})
	public String processNewCauseForm(@Valid final Local local, final BindingResult result, final Map<String, Object> model) {

		if (result.hasErrors()) {
			model.put("local", local);
			return LocalController.VIEWS_CAUSE_CREATE_FORM;
		} else {
			local.setDecision("PENDIENTE");
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Propietario p = this.propietarioService.findByUsername(username);
			local.setPropietario(p);
			this.localService.saveLocal(local);
			return "redirect:/propietario/locales";
		}
	}


}
