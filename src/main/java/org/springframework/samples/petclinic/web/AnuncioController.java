
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PatrocinadorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnuncioController {

	private final AnuncioService		anuncioService;
	private final PatrocinadorService	patrocinadorService;
	private final LocalService			localService;
	private final FiestaService			fiestaService;


	@Autowired
	public AnuncioController(final AnuncioService anuncioService, final PatrocinadorService patrocinadorService, final LocalService localService, final FiestaService fiestaService) {
		this.anuncioService = anuncioService;
		this.patrocinadorService = patrocinadorService;
		this.localService = localService;
		this.fiestaService = fiestaService;
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
		Collection<Anuncio> paraFiestas = new ArrayList<>();
		Collection<Anuncio> paraLocales = new ArrayList<>();
		for (Anuncio a : anuncios) {
			if (a.getLocal() != null) {
				paraLocales.add(a);
			} else {
				paraFiestas.add(a);
			}
		}

		model.put("paraFiestas", paraFiestas);
		model.put("paraLocales", paraLocales);

		return "anuncios/listaAnuncios";
	}

	//Crear para un local
	@GetMapping(value = "/anuncio/new/{targetId}/local")
	public String initCreationFormLocal(@PathVariable("targetId") final int targetId, final ModelMap model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Patrocinador patrocinador = this.patrocinadorService.findByUsername(username);
			if (patrocinador == null) {
				throw new Exception();
			} else {
				Anuncio anuncio = new Anuncio();
				model.put("anuncio", anuncio);
				model.put("targetId", targetId);
				model.put("forLocal", true);
				return "anuncios/new";
			}
		} catch (Exception e) {
			model.put("exception", "No tienes acceso para crear un anuncio");
			return "exception";
		}
	}

	@PostMapping(value = "/anuncio/new/{targetId}/local")
	public String processCreationFormLocal(@PathVariable("targetId") final int targetId, @Valid final Anuncio anuncio, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("anuncio", anuncio);
			model.put("forLocal", true);
			return "anuncios/new";
		} else {
			Patrocinador patrocinador = this.patrocinadorService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			Local local = this.localService.findLocalById(targetId);
			anuncio.setLocal(local);
			anuncio.setPatrocinador(patrocinador);
			anuncio.setDecision("PENDIENTE");
			this.anuncioService.save(anuncio);
			return "redirect:/anuncio/" + anuncio.getId();
		}
	}

	//Crear para una fiesta
	@GetMapping(value = "/anuncio/new/{targetId}/fiesta")
	public String initCreationFormFiesta(@PathVariable("targetId") final int targetId, final ModelMap model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Patrocinador patrocinador = this.patrocinadorService.findByUsername(username);
			if (patrocinador == null) {
				throw new Exception();
			} else {
				Anuncio anuncio = new Anuncio();
				model.put("anuncio", anuncio);
				model.put("targetId", targetId);
				return "anuncios/new";
			}
		} catch (Exception e) {
			model.put("exception", "No tienes acceso para crear un anuncio");
			return "exception";
		}
	}

	@PostMapping(value = "/anuncio/new/{targetId}/fiesta")
	public String processCreationFormFiesta(@PathVariable("targetId") final int targetId, @Valid final Anuncio anuncio, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("anuncio", anuncio);
			return "anuncios/new";
		} else {
			Patrocinador patrocinador = this.patrocinadorService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			Fiesta fiesta = this.fiestaService.findFiestaById(targetId);
			anuncio.setFiesta(fiesta);
			anuncio.setPatrocinador(patrocinador);
			anuncio.setDecision("PENDIENTE");
			this.anuncioService.save(anuncio);
			return "redirect:/anuncio/" + anuncio.getId();
		}
	}

}
