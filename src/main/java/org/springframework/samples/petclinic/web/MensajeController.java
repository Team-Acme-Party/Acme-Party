
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.model.Buzon;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Mensaje;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.samples.petclinic.service.BuzonService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.MensajeService;
import org.springframework.samples.petclinic.service.PatrocinadorService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MensajeController {

	private final PatrocinadorService	patrocinadorService;
	private final ClienteService		clienteService;
	private final PropietarioService	propietarioService;
	private final MensajeService		mensajeService;
	private final AdministradorService	administradorService;
	private final BuzonService			buzonService;


	@Autowired
	public MensajeController(final PatrocinadorService patrocinadorService, final ClienteService clienteService, final PropietarioService propietarioService, final MensajeService mensajeService, final AdministradorService administradorService,
		final BuzonService buzonService) {

		this.patrocinadorService = patrocinadorService;
		this.clienteService = clienteService;
		this.propietarioService = propietarioService;
		this.mensajeService = mensajeService;
		this.administradorService = administradorService;
		this.buzonService = buzonService;
	}

	@GetMapping(value = {
		"/usuario/mensajes"
	})
	public String verMisMensajes(final Map<String, Object> model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Propietario p = this.propietarioService.findByUsername(username);
		Cliente c = this.clienteService.findByUsername(username);
		Patrocinador pa = this.patrocinadorService.findByUsername(username);
		Administrador admin = this.administradorService.findByUsername(username);
		Collection<Mensaje> enviados;
		Collection<Mensaje> recibidos;

		if (p != null) {
			enviados = this.mensajeService.findByBuzonRemitenteId(p.getBuzon().getId());
			recibidos = this.mensajeService.findByBuzonDestinatarioId(p.getBuzon().getId());

		} else if (c != null) {
			enviados = this.mensajeService.findByBuzonRemitenteId(c.getBuzon().getId());
			recibidos = this.mensajeService.findByBuzonDestinatarioId(c.getBuzon().getId());

		} else if (pa != null) {
			enviados = this.mensajeService.findByBuzonRemitenteId(pa.getBuzon().getId());
			recibidos = this.mensajeService.findByBuzonDestinatarioId(pa.getBuzon().getId());

		} else if (admin != null) {
			enviados = this.mensajeService.findByBuzonRemitenteId(admin.getBuzon().getId());
			recibidos = this.mensajeService.findByBuzonDestinatarioId(admin.getBuzon().getId());

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

		Mensaje mensaje = this.mensajeService.findById(mensajeId);
		Buzon remitente = mensaje.getBuzonRemitente();
		Buzon destinatario = mensaje.getBuzonDestinatario();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Patrocinador patrocinador = this.patrocinadorService.findByUsername(username);
		Cliente cliente = this.clienteService.findByUsername(username);
		Propietario propietario = this.propietarioService.findByUsername(username);
		Administrador admin = this.administradorService.findByUsername(username);
		//		Collection<Propietario> propietarios = this.propietarioService.findAll();
		//		Collection<Patrocinador> patrocinadores = this.patrocinadorService.findAll();
		//		Collection<Cliente> clientes = this.clienteService.findAll();
		//		Collection<Administrador> administradores = this.administradorService.findAll();
		//		String usuarioDestinatario = null;
		//		String usuarioRemitente = null;
		//		boolean destinatarioEncontrado = false;
		//		boolean remitenteEncontrado = false;
		//
		//		for (Propietario p : propietarios) {
		//			if (!destinatarioEncontrado) {
		//				if (p.getBuzon().equals(destinatario)) {
		//					usuarioDestinatario = p.toString();
		//					destinatarioEncontrado = true;
		//				}
		//			}
		//			if (!remitenteEncontrado) {
		//				if (p.getBuzon().equals(remitente)) {
		//					usuarioRemitente = p.toString();
		//					remitenteEncontrado = true;
		//				}
		//			}
		//
		//		}
		//		if (!destinatarioEncontrado || !remitenteEncontrado) {
		//			for (Cliente c : clientes) {
		//				if (!destinatarioEncontrado) {
		//					if (c.getBuzon().equals(destinatario)) {
		//						usuarioDestinatario = c.toString();
		//						destinatarioEncontrado = true;
		//					}
		//				}
		//				if (!remitenteEncontrado) {
		//					if (c.getBuzon().equals(remitente)) {
		//						usuarioRemitente = c.toString();
		//						remitenteEncontrado = true;
		//					}
		//				}
		//
		//			}
		//		}
		//
		//		if (!destinatarioEncontrado || !remitenteEncontrado) {
		//			for (Patrocinador p : patrocinadores) {
		//				if (!destinatarioEncontrado) {
		//					if (p.getBuzon().equals(destinatario)) {
		//						usuarioDestinatario = p.toString();
		//						destinatarioEncontrado = true;
		//					}
		//				}
		//				if (!remitenteEncontrado) {
		//					if (p.getBuzon().equals(remitente)) {
		//						usuarioRemitente = p.toString();
		//						remitenteEncontrado = true;
		//					}
		//				}
		//
		//			}
		//		}
		//
		//		if (!destinatarioEncontrado || !remitenteEncontrado) {
		//			for (Administrador a : administradores) {
		//				if (!destinatarioEncontrado) {
		//					if (a.getBuzon().equals(destinatario)) {
		//						usuarioDestinatario = a.toString();
		//						destinatarioEncontrado = true;
		//					}
		//				}
		//				if (!remitenteEncontrado) {
		//					if (a.getBuzon().equals(remitente)) {
		//						usuarioRemitente = a.toString();
		//						remitenteEncontrado = true;
		//					}
		//				}
		//
		//			}
		//		}

		if (patrocinador != null) {
			if (patrocinador.getBuzon().equals(remitente) || patrocinador.getBuzon().equals(destinatario)) {
				//				mav.addObject(usuarioDestinatario);
				//				mav.addObject(usuarioRemitente);
			} else {
				mav = new ModelAndView("exception");
				mav.addObject("message", "No puede ver ese mensaje");
			}
		} else if (cliente != null) {
			if (cliente.getBuzon().equals(remitente) || cliente.getBuzon().equals(destinatario)) {
				//				mav.addObject(usuarioDestinatario);
				//				mav.addObject(usuarioRemitente);
			} else {
				mav = new ModelAndView("exception");
				mav.addObject("message", "No puede ver ese mensaje");
			}
		} else if (propietario != null) {
			if (propietario.getBuzon().equals(remitente) || propietario.getBuzon().equals(destinatario)) {
				//				mav.addObject(usuarioDestinatario);
				//				mav.addObject(usuarioRemitente);
			} else {
				mav = new ModelAndView("exception");
				mav.addObject("message", "No puede ver ese mensaje");
			}
		} else if (admin != null) {
			//			mav.addObject(usuarioDestinatario);
			//			mav.addObject(usuarioRemitente);

		} else {
			mav = new ModelAndView("exception");
			mav.addObject("message", "No puede ver ese mensaje");
		}
		mav.addObject(mensaje);
		return mav;
	}

}
