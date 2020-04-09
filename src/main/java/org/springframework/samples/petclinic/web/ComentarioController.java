
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;;

@Controller
public class ComentarioController {

	private final FiestaService			fiestaService;
	private final LocalService			localService;
	private final ClienteService		clienteService;
	private final ComentarioService		comentarioService;


	@Autowired
	public ComentarioController(final FiestaService fiestaService, final LocalService localService,
			final ClienteService clienteService, final ComentarioService comentarioService) {
		this.fiestaService = fiestaService;
		this.localService = localService;
		this.clienteService = clienteService;
		this.comentarioService= comentarioService;

	}

	@PostMapping(value = {
			"/comentario/new/local/{localId}"
		})
	public String saveComentarioLocal(@PathVariable("localId") final int localId,  @Valid final Comentario comentario, final BindingResult result, final Map<String, Object> model) {
			
		if (result.hasErrors()) {
			return "redirect:/locales/"+ localId;
		} else {
			Local local = this.localService.findLocalById(localId);
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Cliente c = this.clienteService.findByUsername(username);
			comentario.setCliente(c);
			comentario.setFecha(LocalDate.now());
			comentario.setLocal(local);
			
			this.comentarioService.save(comentario);
			
			return "redirect:/locales/"+ localId;
		
	}
	}
	
	@PostMapping(value = {
			"/comentario/new/fiesta/{fiestaId}"
		})
	public String saveComentarioFiesta(@PathVariable("fiestaId") final int fiestaId,  @Valid final Comentario comentario, final BindingResult result, final Map<String, Object> model) {
			
		if (result.hasErrors()) {
			return "redirect:/fiestas/"+ fiestaId;
		} else {
			Fiesta fiesta = this.fiestaService.findFiestaById(fiestaId);
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Cliente c = this.clienteService.findByUsername(username);
			comentario.setCliente(c);
			comentario.setFecha(LocalDate.now());
			comentario.setFiesta(fiesta);
			
			this.comentarioService.save(comentario);
			
			return "redirect:/fiestas/"+ fiestaId;
		
	}
	}

}
