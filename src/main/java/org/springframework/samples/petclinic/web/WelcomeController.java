
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	private LocalService	localService;
	private FiestaService	fiestaService;


	@Autowired
	public WelcomeController(final LocalService localService, final FiestaService fiestaService) {
		this.localService = localService;
		this.fiestaService = fiestaService;
	}

	@GetMapping({
		"/", "/welcome"
	})
	public String welcome(final Map<String, Object> model) {

		Collection<Local> locales = this.localService.findAccepted();
		Collection<Fiesta> fiestas = this.fiestaService.findAccepted();
		model.put("locales", locales);
		model.put("fiestas", fiestas);

		return "welcome";
	}
}
