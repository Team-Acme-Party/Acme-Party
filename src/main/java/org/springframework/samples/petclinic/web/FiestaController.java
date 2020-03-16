
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.stereotype.Controller;
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

}
