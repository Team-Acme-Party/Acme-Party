package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@Autowired
	private AdministradorService administratorService;

	
	@GetMapping(value = { "administrador/dashboard" })
	public String todosLosLocales(final Map<String, Object> model) {
		model.put("name", "dashboard");
		
		model.put("localA", this.administratorService.localAceptado());
		model.put("localP", this.administratorService.localPendiente());
		model.put("localR", this.administratorService.localRechazado());
		
		model.put("fiestaA", this.administratorService.fiestaAceptado());
		model.put("fiestaP", this.administratorService.fiestaPendiente());
		model.put("fiestaR", this.administratorService.fiestaRechazado());
		
		model.put("solicitudA", this.administratorService.solicitudAceptado());
		model.put("solicitudP", this.administratorService.solicitudPendiente());
		model.put("solicitudR", this.administratorService.solicitudRechazado());
		
		return "admin/dashboard";
	}
}
