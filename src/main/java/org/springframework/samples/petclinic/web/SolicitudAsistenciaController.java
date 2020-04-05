
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.SolicitudAsistenciaService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SolicitudAsistenciaController {

//	@Autowired
	private final SolicitudAsistenciaService solicitudAsistenciaService;

//	@Autowired
	private ClienteService clienteService;

	@Autowired
	public SolicitudAsistenciaController(final SolicitudAsistenciaService solicitudAsistenciaService,
			final ClienteService clienteService) {
		this.solicitudAsistenciaService = solicitudAsistenciaService;
		this.clienteService = clienteService;
	}

	@GetMapping(value = { "/cliente/solicitudesAsistencias" })
	public String verMisAsistencias(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Cliente c = this.clienteService.findByUsername(username);
		Collection<SolicitudAsistencia> asistencias = this.solicitudAsistenciaService
				.findAsistenciasByClienteId(c.getId());
		model.put("asistencias", asistencias);
		model.put("misasistencias", true);

		return "solicitudesAsistencia/listaSolicitudesAsistencia";
	}

	@GetMapping("/cliente/solicitudAsistencia/fiesta/{fiestaId}")
	public String crearSolicitudAsistencia(@RequestParam("fiestaId") int fiestaId, final Map<String, Object> model) {

		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Assert.notNull(username, "Username no logueado");
			Cliente cliente = clienteService.findByUsername(username);
			Assert.notNull(cliente, "Cliente no logueado");

			SolicitudAsistencia sol = solicitudAsistenciaService.create(fiestaId, cliente);
			solicitudAsistenciaService.save(sol);
			return verMisAsistencias(model);

		} catch (Exception e) {
			model.put("message", e.getMessage());
			return "exception";
		}
	}
}
