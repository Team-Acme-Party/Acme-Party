
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.stereotype.Controller;
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

}
