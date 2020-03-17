
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FiestaController {

	private final FiestaService fiestaService;


	@Autowired
	public FiestaController(final FiestaService fiestaService) {
		this.fiestaService = fiestaService;
	}

	@GetMapping(value = {
		"/fiestas/{fiestaId}"
	})
	public ModelAndView showFiesta(@PathVariable("fiestaId") final int fiestaId) {
		ModelAndView mav = new ModelAndView("fiestas/fiestaDetails");
		mav.addObject(this.fiestaService.findFiestaById(fiestaId));
		return mav;
	}

	@GetMapping(value = {
		"/fiestas/buscar"
	})
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
		}
		//		else if (results.size() == 1) {
		//			Fiesta res = results.iterator().next();
		//			return "redirect:/fiestas/" + res.getId();}
		else {
			model.put("fiestas", results);
			return "fiestas/listaFiestas";
		}
	}

}
