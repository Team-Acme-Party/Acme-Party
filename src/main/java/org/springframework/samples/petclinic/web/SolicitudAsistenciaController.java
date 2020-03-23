
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
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SolicitudAsistenciaController {

	private final SolicitudAsistenciaService	solicitudAsistenciaService;
	private ClienteService						clienteService;


	@Autowired
	public SolicitudAsistenciaController(final SolicitudAsistenciaService solicitudAsistenciaService, final ClienteService clienteService) {
		this.solicitudAsistenciaService = solicitudAsistenciaService;
		this.clienteService = clienteService;
	}

	@GetMapping(value = {
		"/cliente/solicitudesAsistencias"
	})
	public String verMisAsistencias(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Cliente c = this.clienteService.findByUsername(username);
		Collection<SolicitudAsistencia> asistencias = this.solicitudAsistenciaService.findAsistenciasByClienteId(c.getId());
		model.put("asistencias", asistencias);
		model.put("misasistencias", true);

		return "solicitudesAsistencia/listaSolicitudesAsistencia";
	}

}
