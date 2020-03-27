
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.PatrocinadorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnuncioController {

	private final AnuncioService	anuncioService;
	private final PatrocinadorService     patrocinadorService;


	@Autowired
	public AnuncioController(final AnuncioService anuncioService, final PatrocinadorService patrocinadorService) {
		this.anuncioService = anuncioService;
		this.patrocinadorService = patrocinadorService;
	}

	@GetMapping(value = {
		"/anuncio/{anuncioId}"
	})
	public ModelAndView showAnuncio(@PathVariable("anuncioId") final int anuncioId) {
		ModelAndView mav = new ModelAndView("anuncios/anuncioDetails");
		mav.addObject(this.anuncioService.findById(anuncioId));
		return mav;
	}

	@GetMapping(value = {
		"/patrocinador/anuncios"
	})
	public String verMisAnuncios(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Patrocinador p = this.patrocinadorService.findByUsername(username);
		Collection<Anuncio> anuncios = this.anuncioService.findByPatrocinadorId(p.getId());
		model.put("anuncios", anuncios);

		return "anuncios/listaAnuncios";
	}

}
