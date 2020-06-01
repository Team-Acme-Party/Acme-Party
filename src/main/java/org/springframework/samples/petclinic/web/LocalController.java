
package org.springframework.samples.petclinic.web;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.SolicitudAsistenciaService;
import org.springframework.samples.petclinic.service.ValoracionService;
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
	private final ClienteService clienteService;
	private final FiestaService fiestaService;
	private final AnuncioService anuncioService;
	private final ComentarioService comentarioService;
	private final ValoracionService valoracionService;
	private final SolicitudAsistenciaService solicitudAsistenciaService;
	private final AdministradorService administradorService;

	@Autowired
	public LocalController(final LocalService localService, final PropietarioService propietarioService,
			final FiestaService fiestaService, final AnuncioService anuncioService,
			final ComentarioService comentarioService, final ValoracionService valoracionService,
			final SolicitudAsistenciaService solicitudAsistenciaService, final ClienteService clienteService,
			final AdministradorService administradorService) {
		this.localService = localService;
		this.propietarioService = propietarioService;
		this.fiestaService = fiestaService;
		this.anuncioService = anuncioService;
		this.comentarioService = comentarioService;
		this.valoracionService = valoracionService;
		this.solicitudAsistenciaService = solicitudAsistenciaService;
		this.clienteService = clienteService;
		this.administradorService = administradorService;
	}

	@GetMapping(value = { "/locales/{localId}" })
	public ModelAndView showLocal(@PathVariable("localId") final int localId) {
		ModelAndView mav;
		LocalTime now = LocalTime.now();
		Local local = this.localService.findLocalById(localId);
		Cliente c = this.clienteService.getClienteLogado();
		Propietario propietario = this.propietarioService.getPropietarioLogado();
		Administrador admin = this.administradorService.getAdministradorLogado();

		if (local.getDecision().equals("ACEPTADO") || admin != null
				|| this.localService.findByPropietarioId(propietario.getId()).contains(local)) {
			mav = new ModelAndView("locales/localDetails");
			Collection<Anuncio> anuncios = this.anuncioService.findByLocalId(localId);
			Collection<Comentario> comentarios = this.comentarioService.findByLocalId(localId);
			Collection<Valoracion> valoraciones = this.valoracionService.findByLocalId(localId);
			mav.addObject("valoraciones", valoraciones);
			mav.addObject("comentarios", comentarios);
			mav.addObject(this.localService.findLocalById(localId));
			mav.addObject("anuncios", anuncios);
			Comentario comentario = new Comentario();
			mav.addObject("comentario", comentario);

			if (c != null) {
				mav.addObject("cliente", c);
				Collection<Fiesta> fiestasCliente = this.solicitudAsistenciaService
						.findSolicitudFiestaByClienteId(c.getId());
				for (Fiesta f : fiestasCliente) {
					if (f.getLocal() == this.localService.findLocalById(localId) && f.getHoraFin().isBefore(now)) {
						mav.addObject("clienteValoracion", true);
						Valoracion valoracion = new Valoracion();
						mav.addObject("valoracion", valoracion);
					}
				}
			}
		} else {
			mav = new ModelAndView("exception");
			mav.addObject("message", "No puede ver este local");
		}
		return mav;
	}

	@GetMapping(value = { "/locales/buscar" })
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

		Collection<Local> results = this.localService.findByDireccion(local.getDireccion());
		if (results.isEmpty()) {
			result.rejectValue("direccion", "notFound", "not found");
			return "locales/buscarLocales";
		} else {
			model.put("locales", results);
			return "locales/listaLocales";
		}
	}

	@GetMapping(value = { "/propietario/locales" })
	public String verMisLocales(final Map<String, Object> model) {

		Propietario p = this.propietarioService.getPropietarioLogado();
		Collection<Local> locales = this.localService.findByPropietarioId(p.getId());
		model.put("locales", locales);
		model.put("mislocales", true);

		return "locales/listaLocales";
	}

	@GetMapping(value = { "/local/{localId}/fiestas" })
	public String verSolicitudes(@PathVariable("localId") final int localId, final Map<String, Object> model) {

		Collection<Fiesta> fiestas = this.fiestaService.findFiestasPendientesByLocalId(localId);

		model.put("fiestas", fiestas);
		return "fiestas/listaFiestas";
	}

	@GetMapping(value = { "/local/{localId}/fiesta/{fiestaId}/aceptar" })
	public String aceptarSolicitud(@PathVariable("fiestaId") final int fiestaId,
			@PathVariable("localId") final int localId, final Map<String, Object> model) {
		try {
			Propietario propietario = this.propietarioService.getPropietarioLogado();
			Fiesta f = this.fiestaService.findFiestaById(fiestaId);

			if (noEsPropietarioDelLocal(propietario, localId) || !f.getDecision().equals("PENDIENTE")) {
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
			model.put("message", "error");
			return "exception";
		}
	}

	@GetMapping(value = { "/local/{localId}/fiesta/{fiestaId}/denegar" })
	public String denegarSolicitud(@PathVariable("fiestaId") final int fiestaId,
			@PathVariable("localId") final int localId, final Map<String, Object> model) {
		try {
			Propietario propietario = this.propietarioService.getPropietarioLogado();
			Fiesta f = this.fiestaService.findFiestaById(fiestaId);

			if (noEsPropietarioDelLocal(propietario, localId) || !f.getDecision().equals("PENDIENTE")) {
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
			model.put("message", "error");
			return "exception";
		}
	}

	@GetMapping(value = { "administrador/locales" })
	public String todosLosLocales(final Map<String, Object> model) {

		Collection<Local> locales = this.localService.findPending();
		model.put("locales", locales);
		model.put("mislocales", true);

		return "locales/listaLocales";
	}

	@GetMapping(value = { "/administrador/local/{localId}/rechazar" })
	public String denegarSolicitudLocal(@PathVariable("localId") final int localId, final Map<String, Object> model) {
		Local local = this.localService.denegarSolicitudLocal(localId);

		model.put("local", local);
		return "redirect:/administrador/locales";
	}

	@GetMapping(value = { "/administrador/local/{localId}/aceptar" })
	public String aceptarSolicitudLocal(@PathVariable("localId") final int localId, final Map<String, Object> model) {
		Local local = this.localService.aceptarSolicitudLocal(localId);

		model.put("local", local);
		return "redirect:/administrador/locales";
	}

	@GetMapping(value = { "/locales/new" })
	public String initCreationForm(final Map<String, Object> model) {
		Propietario propietario = this.propietarioService.getPropietarioLogado();
		if (propietario != null) {
			Local local = new Local();
			model.put("local", local);
			return VIEWS_CAUSE_CREATE_FORM;
		} else {
			model.put("message", "No estas autorizado para crear un local");
			return "exception";
		}

	}

	@PostMapping(value = { "/locales/new" })
	public String processNewCauseForm(@Valid final Local local, final BindingResult result,
			final Map<String, Object> model) {

		if (result.hasErrors()) {
			model.put("local", local);
			return VIEWS_CAUSE_CREATE_FORM;
		} else {
			local.setDecision("PENDIENTE");
			Propietario p = this.propietarioService.getPropietarioLogado();
			local.setPropietario(p);
			this.localService.saveLocal(local);
			return "redirect:/propietario/locales";
		}
	}

	private Boolean noEsPropietarioDelLocal(Propietario propietario, Integer localId) {
		return propietario == null || this.localService.findLocalById(localId).getPropietario() != propietario;
	}

}
