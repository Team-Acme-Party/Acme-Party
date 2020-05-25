
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Mensaje;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.MensajeService;
import org.springframework.samples.petclinic.service.PatrocinadorService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MensajeController {

	private final PatrocinadorService	patrocinadorService;
	private final ClienteService		clienteService;
	private final PropietarioService	propietarioService;
	private final MensajeService		mensajeService;
	private final AdministradorService	administradorService;
	private final UserService			userService;


	@Autowired
	public MensajeController(final PatrocinadorService patrocinadorService, final ClienteService clienteService, final PropietarioService propietarioService, final MensajeService mensajeService, final AdministradorService administradorService,
		final UserService userService) {

		this.patrocinadorService = patrocinadorService;
		this.clienteService = clienteService;
		this.propietarioService = propietarioService;
		this.mensajeService = mensajeService;
		this.administradorService = administradorService;
		this.userService = userService;
	}

	@GetMapping(value = {
		"/usuario/mensajes"
	})
	public String verMisMensajes(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<Mensaje> enviados;
		Collection<Mensaje> recibidos;
		Propietario p = this.propietarioService.findByUsername(username);
		Patrocinador pa = this.patrocinadorService.findByUsername(username);
		Cliente c = this.clienteService.findByUsername(username);
		Administrador a = this.administradorService.findByUsername(username);
		Boolean existeUsuario = username != null && (p != null || pa != null || c != null || a != null);
		if (existeUsuario) {
			enviados = this.mensajeService.findByRemitente(username);
			recibidos = this.mensajeService.findByDestinatario(username);

		} else {
			model.put("message", "No tiene acceso a esta ruta");
			return "exception";
		}

		model.put("enviados", enviados);
		model.put("recibidos", recibidos);
		return "mensajes/listaMensajes";
	}

	@GetMapping(value = {
		"/mensajes/{mensajeId}"
	})
	public ModelAndView showMensaje(@PathVariable("mensajeId") final int mensajeId) {
		ModelAndView mav;
		mav = new ModelAndView("mensajes/mensajeDetails");

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Mensaje mensaje = this.mensajeService.findById(mensajeId);

		if (mensaje.getRemitente().equals(username) || mensaje.getDestinatario().equals(username)) {
			mav.addObject(mensaje);

		} else {
			mav = new ModelAndView("exception");
			mav.addObject("message", "No puede ver ese mensaje");
		}

		return mav;
	}
	//Crear un mensaje

	@GetMapping(value = "/mensajes/new")
	public String initCreationFormMensaje(final ModelMap model) {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			if (username == null) {
				throw new Exception();
			} else {
				Mensaje mensaje = new Mensaje();

				Collection<User> users = this.userService.findAll();
				List<String> usuarios = new ArrayList<String>();
				for (User s : users) {
					usuarios.add(s.getUsername());
				}
				model.put("usuarios", usuarios);
				model.put("mensaje", mensaje);
				return "mensajes/new";
			}
		} catch (Exception e) {
			model.put("exception", "No tienes acceso para crear un mensaje");

			return "exception";
		}
	}

	@PostMapping(value = "/mensajes/new")
	public String processCreationFormMensaje(@Valid final Mensaje mensaje, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.put("mensaje", mensaje);
			Collection<User> users = this.userService.findAll();
			List<String> usuarios = new ArrayList<String>();
			for (User s : users) {
				usuarios.add(s.getUsername());
			}
			model.put("usuarios", usuarios);
			return "mensajes/new";
		} else {

			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			mensaje.setRemitente(username);
			mensaje.setFecha(LocalDate.now());
			mensaje.setHora(LocalTime.now());
			this.mensajeService.save(mensaje);

			return "redirect:/usuario/mensajes";
		}
	}

}
