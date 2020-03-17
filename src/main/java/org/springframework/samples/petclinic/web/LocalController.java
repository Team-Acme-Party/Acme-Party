
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LocalController {

	private final LocalService localService;


	@Autowired
	public LocalController(final LocalService localService) {
		this.localService = localService;
	}

	@GetMapping(value = {
		"/locales/{localId}"
	})
	public ModelAndView showLocal(@PathVariable("localId") final int localId) {
		ModelAndView mav = new ModelAndView("locales/localDetails");
		mav.addObject(this.localService.findLocalById(localId));
		return mav;
	}

	@GetMapping(value = {
		"/locales/buscar"
	})
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
		//		else if (results.size() == 1) {
		//			Local res = results.iterator().next();
		//			return "redirect:/locales/" + res.getId();}
		else {
			model.put("locales", results);
			return "locales/listaLocales";
		}
	}

}
