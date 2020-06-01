package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.ValoracionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ValoracionController {

	private final FiestaService		fiestaService;
	private final LocalService		localService;
	private final ClienteService	clienteService;
	private final ValoracionService	valoracionService;


			@Autowired
			public ValoracionController(final FiestaService fiestaService, final LocalService localService, final ClienteService clienteService, final ValoracionService valoracionService) {
				this.fiestaService = fiestaService;
				this.localService = localService;
				this.clienteService = clienteService;
				this.valoracionService = valoracionService;

			}

			@PostMapping(value = {
				"/valoracion/new/local/{localId}"
			})
			public String saveValoracionLocal(@PathVariable("localId") final int localId, @Valid final Valoracion valoracion, final BindingResult result, final Map<String, Object> model) {

				if (result.hasErrors()) {
					return "redirect:/locales/" + localId;
				} else {
					Local local = this.localService.findLocalById(localId);
					Cliente c = this.clienteService.getClienteLogado();
					valoracion.setCliente(c);
					valoracion.setLocal(local);

					this.valoracionService.save(valoracion);

					return "redirect:/locales/" + localId;

				}
			}

			@PostMapping(value = {
				"/valoracion/new/fiesta/{fiestaId}"
			})
			public String saveValoracionFiesta(@PathVariable("fiestaId") final int fiestaId, @Valid final Valoracion valoracion, final BindingResult result, final Map<String, Object> model) {

				if (result.hasErrors()) {
					return "redirect:/fiestas/" + fiestaId;
				} else {
					Fiesta fiesta = this.fiestaService.findFiestaById(fiestaId);
					Cliente c = this.clienteService.getClienteLogado();
					valoracion.setCliente(c);
					valoracion.setFiesta(fiesta);

					this.valoracionService.save(valoracion);

					return "redirect:/fiestas/" + fiestaId;

				}
			}
}
