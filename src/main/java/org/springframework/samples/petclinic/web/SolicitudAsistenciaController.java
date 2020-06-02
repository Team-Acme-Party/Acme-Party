
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.SolicitudAsistenciaService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SolicitudAsistenciaController {

	@Autowired
	private final SolicitudAsistenciaService solicitudAsistenciaService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	public SolicitudAsistenciaController(final SolicitudAsistenciaService solicitudAsistenciaService,
			final ClienteService clienteService) {
		this.solicitudAsistenciaService = solicitudAsistenciaService;
		this.clienteService = clienteService;
	}

	@GetMapping(value = { "/cliente/solicitudesAsistencias" })
	public String verMisAsistencias(final Map<String, Object> model) {

		Cliente c = clienteService.getClienteLogado();
		Assert.notNull(c, "Cliente no logueado");
		Collection<SolicitudAsistencia> asistencias = this.solicitudAsistenciaService
				.findAsistenciasByClienteId(c.getId());
		model.put("asistencias", asistencias);
		model.put("misasistencias", true);

		return "solicitudesAsistencia/listaSolicitudesAsistencia";
	}

	@GetMapping(value = { "/cliente/solicitudAsistencia/fiesta/{fiestaId}" })
	public String crearSolicitudAsistencia(@RequestParam("fiestaId") int fiestaId, final Map<String, Object> model) {
		try {
			Cliente cliente = clienteService.getClienteLogado();
			Assert.notNull(cliente, "Cliente no logueado");
			SolicitudAsistencia sol = solicitudAsistenciaService.create(fiestaId, cliente);
			solicitudAsistenciaService.save(sol);
			return verMisAsistencias(model);

		} catch (Exception e) {
			model.put("message", e.getMessage());
			return "exception";
		}
	}

	@GetMapping("/cliente/solicitudAsistencia/aceptar/{solicitudId}")
	public String aceptarSolicitudAsistencia(@PathVariable("solicitudId") int solicitudId,
			final Map<String, Object> model) {

		try {
			Cliente cliente = clienteService.getClienteLogado();
			Assert.notNull(cliente, "Cliente no logueado");
			this.solicitudAsistenciaService.aceptarSolicitud(solicitudId, cliente);
			return "redirect:/cliente/fiestas";

		} catch (Exception e) {
			model.put("message", e.getMessage());
			return "exception";
		}
	}

	@GetMapping("/cliente/solicitudAsistencia/rechazar/{solicitudId}")
	public String rechazarSolicitudAsistencia(@PathVariable("solicitudId") int solicitudId,
			final Map<String, Object> model) {

		try {
			Cliente cliente = clienteService.getClienteLogado();
			Assert.notNull(cliente, "Cliente no logueado");

			this.solicitudAsistenciaService.rechazarSolicitud(solicitudId, cliente);
			return "redirect:/cliente/fiestas";

		} catch (Exception e) {
			model.put("message", e.getMessage());
			return "exception";
		}
	}
}
