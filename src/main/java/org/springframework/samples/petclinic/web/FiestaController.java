
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;;

@Controller
public class FiestaController {

	private final FiestaService fiestaService;
	private final LocalService localService;
	private final ClienteService clienteService;
	private final PropietarioService propietarioService;
	private final AnuncioService anuncioService;
	private final ComentarioService comentarioService;
	private final ValoracionService valoracionService;
	private final SolicitudAsistenciaService solicitudAsistenciaService;
	private final AdministradorService administradorService;

	@Autowired
	public FiestaController(final FiestaService fiestaService, final LocalService localService,
			final ClienteService clienteService, final PropietarioService propietarioService,
			final AnuncioService anuncioService, final ComentarioService comentarioService,
			final ValoracionService valoracionService, final SolicitudAsistenciaService solicitudAsistenciaService,
			final AdministradorService administradorService) {
		this.fiestaService = fiestaService;
		this.localService = localService;
		this.clienteService = clienteService;
		this.propietarioService = propietarioService;
		this.anuncioService = anuncioService;
		this.comentarioService = comentarioService;
		this.valoracionService = valoracionService;
		this.solicitudAsistenciaService = solicitudAsistenciaService;
		this.administradorService = administradorService;
	}

	@GetMapping(value = "/fiestas/{fiestaId}")
	public ModelAndView showFiesta(@PathVariable("fiestaId") final int fiestaId) {
		ModelAndView mav;
		LocalDate now = LocalDate.now();

		Fiesta fiesta = this.fiestaService.findFiestaById(fiestaId);
		Propietario p = this.propietarioService.getPropietarioLogado();
		Cliente cliente = this.clienteService.getClienteLogado();
		Administrador admin = this.administradorService.getAdministradorLogado();

		if (fiesta.getDecision().equals("ACEPTADO") || admin != null || esFiestaDelCliente(cliente, fiesta)
				|| esFiestaDelPropietario(p, fiesta)) {
			mav = new ModelAndView("fiestas/fiestaDetails");

			if (p != null) {
				mav.addObject("userLoggedId", p.getId());
			} else if (cliente != null) {
				mav.addObject("userLoggedId", cliente.getId());

				Collection<Fiesta> solicitudesCliente = this.fiestaService.findAsistenciasByClienteId(cliente.getId());
				Boolean esFiestaSolicitadaPorCliente = solicitudesCliente.contains(fiesta);
				Collection<SolicitudAsistencia> solicitudes = this.solicitudAsistenciaService.findByFiesta(fiesta);
				mav.addObject("esFiestaSolicitadaPorCliente", esFiestaSolicitadaPorCliente);
				mav.addObject("solicitudes", solicitudes);
				Comentario comentario = new Comentario();
				mav.addObject("comentario", comentario);

				Collection<Fiesta> fiestasCliente = this.solicitudAsistenciaService
						.findSolicitudFiestaByClienteId(cliente.getId());
				if (fiesta.getFecha().isBefore(now)
						&& fiestasCliente.contains(this.fiestaService.findFiestaById(fiestaId))) {
					mav.addObject("clienteValoracion", true);
					Valoracion valoracion = new Valoracion();
					mav.addObject("valoracion", valoracion);
				}
			}

			Collection<Anuncio> anuncios = this.anuncioService.findByFiestaId(fiestaId);
			Collection<Comentario> comentarios = this.comentarioService.findByFiestaId(fiestaId);
			Collection<Valoracion> valoraciones = this.valoracionService.findByFiestaId(fiestaId);

			mav.addObject("valoraciones", valoraciones);
			mav.addObject("comentarios", comentarios);
			mav.addObject("fiesta", this.fiestaService.findFiestaById(fiestaId));
			mav.addObject("anuncios", anuncios);
		} else

		{
			mav = new ModelAndView("exception");
			mav.addObject("message", "No puede ver esta fiesta");
		}
		return mav;
	}

	@GetMapping(value = { "/fiestas/buscar" })
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

	@GetMapping(value = { "/cliente/fiestas" })
	public String verMisFiestas(final Map<String, Object> model) {

		Cliente c = this.clienteService.getClienteLogado();
		Collection<Fiesta> fiestas = this.fiestaService.findByClienteId(c.getId());
		model.put("fiestas", fiestas);
		model.put("misfiestas", true);

		return "fiestas/listaFiestas";
	}

	@GetMapping(value = "/fiestas/new/{localId}")
	public String initCreationForm(@PathVariable("localId") final int localId, final ModelMap model) {
		try {
			Cliente cliente = this.clienteService.getClienteLogado();
			Local local = this.localService.findLocalById(localId);

			if (cliente == null || !local.getDecision().equals("ACEPTADO")) {
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
	public String processCreationForm(@PathVariable("localId") final int localId, @Valid final Fiesta fiesta,
			final BindingResult result, final ModelMap model) {

		Local local = this.localService.findLocalById(localId);
		validacionesEditarYCrear(fiesta, local, result);

		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("fiesta", fiesta);
			return "fiestas/new";
		} else {
			Cliente cliente = this.clienteService.getClienteLogado();
			fiesta.setNumeroAsistentes(0);
			fiesta.setLocal(local);
			fiesta.setCliente(cliente);
			fiesta.setDecision("PENDIENTE");
			this.fiestaService.save(fiesta);
			return "redirect:/fiestas/" + fiesta.getId();
		}
	}

	@GetMapping(value = "/fiestas/{fiestaId}/editar")
	public String initUpdateForm(@PathVariable("fiestaId") final int fiestaId, final ModelMap model) {
		Fiesta fiesta = this.fiestaService.findFiestaById(fiestaId);

		try {
			Cliente cliente = this.clienteService.getClienteLogado();
			if (!esFiestaDelCliente(cliente, fiesta)) {
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

	@PostMapping(value = "/fiestas/{fiestaId}/editar")
	public String processUpdateForm(@Valid final Fiesta fiesta, final BindingResult result,
			@PathVariable("fiestaId") final int fiestaId, final ModelMap model) {

		Fiesta f = fiestaService.findFiestaById(fiestaId);
		Local local = f.getLocal();
		validacionesEditarYCrear(fiesta, local, result);

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

	private Boolean esFiestaDelCliente(Cliente cliente, Fiesta fiesta) {
		return cliente != null && this.fiestaService.findByClienteId(cliente.getId()).contains(fiesta);
	}

	private Boolean esFiestaDelPropietario(Propietario p, Fiesta fiesta) {
		return p != null && this.localService.findByPropietarioId(p.getId()).contains(fiesta.getLocal());
	}

	private void validacionesEditarYCrear(Fiesta fiesta, Local local, BindingResult result) {
		if (fiesta.getAforo() == null || local.getCapacidad() == null || fiesta.getAforo() > local.getCapacidad()) {
			result.rejectValue("aforo", "ValidateNumeroAsistentesMayor");
		}
		if (fiesta.getFecha() == null || fiesta.getFecha().compareTo(LocalDate.now()) < 0) {
			result.rejectValue("fecha", "ValidateFechaFutura");
		}
	}
	
}
