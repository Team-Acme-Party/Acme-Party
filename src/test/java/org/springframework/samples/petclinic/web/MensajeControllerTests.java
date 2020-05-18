
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
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
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = MensajeController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class MensajeControllerTests {

	@MockBean
	private PatrocinadorService		patrocinadorService;

	@MockBean
	private MensajeService			mensajeService;

	@MockBean
	private PropietarioService		propietarioService;

	@MockBean
	private ClienteService			clienteService;

	@MockBean
	private AdministradorService	administradorService;

	@MockBean
	private BuzonService			buzonService;

	@Autowired
	private MockMvc					mockMvc;

	private Patrocinador			patrocinador;
	private Propietario				propietario;
	private Cliente					cliente;
	private Administrador			administrador;
	private Mensaje					mensaje;
	private Mensaje					mensaje2;
	private Mensaje					mensaje3;
	private Mensaje					mensaje4;
	private Buzon					buzon;
	private Buzon					buzon2;
	private Buzon					buzon3;
	private Buzon					buzon4;


	@BeforeEach
	void datosIniciales() {

		this.buzon = new Buzon();
		this.buzon.setId(10);
		this.buzon2 = new Buzon();
		this.buzon2.setId(11);
		this.buzon3 = new Buzon();
		this.buzon3.setId(12);
		this.buzon4 = new Buzon();
		this.buzon4.setId(13);

		this.patrocinador = new Patrocinador();
		this.patrocinador.setApellidos("Apellidos prueba");
		this.patrocinador.setEmail("email@prueba.es");
		this.patrocinador.setFoto("http://url.com/%22");
		this.patrocinador.setId(10);
		this.patrocinador.setNombre("patrocinador");
		this.patrocinador.setTelefono("654321987");
		this.patrocinador.setDescripcionExperiencia("Descipción experiencia");
		this.patrocinador.setBuzon(this.buzon);
		BDDMockito.given(this.patrocinadorService.findByUsername("patrocinador")).willReturn(this.patrocinador);

		this.propietario = new Propietario();
		this.propietario.setApellidos("Apellidos prueba");
		this.propietario.setEmail("email@prueba.es");
		this.propietario.setFoto("http://url.com/%22");
		this.propietario.setId(11);
		this.propietario.setNombre("propietario");
		this.propietario.setTelefono("654321987");
		this.propietario.setBuzon(this.buzon2);
		BDDMockito.given(this.propietarioService.findByUsername("propietario")).willReturn(this.propietario);

		this.cliente = new Cliente();
		this.cliente.setApellidos("Apellidos prueba");
		this.cliente.setEmail("email@prueba.es");
		this.cliente.setFoto("http://url.com/%22");
		this.cliente.setId(12);
		this.cliente.setNombre("cliente");
		this.cliente.setTelefono("654321987");
		this.cliente.setBuzon(this.buzon3);
		BDDMockito.given(this.clienteService.findByUsername("cliente")).willReturn(this.cliente);

		this.administrador = new Administrador();
		this.administrador.setApellidos("Apellidos prueba");
		this.administrador.setEmail("email@prueba.es");
		this.administrador.setFoto("http://url.com/%22");
		this.administrador.setId(13);
		this.administrador.setNombre("administrador");
		this.administrador.setTelefono("654321987");
		this.administrador.setBuzon(this.buzon4);
		BDDMockito.given(this.administradorService.findByUsername("administrador")).willReturn(this.administrador);

		this.mensaje = new Mensaje();
		this.mensaje.setId(10);
		this.mensaje.setBuzonRemitente(this.buzon);
		this.mensaje.setBuzonDestinatario(this.buzon2);
		this.mensaje2 = new Mensaje();
		this.mensaje2.setId(11);
		this.mensaje2.setBuzonRemitente(this.buzon2);
		this.mensaje2.setBuzonDestinatario(this.buzon3);

		this.mensaje3 = new Mensaje();
		this.mensaje3.setId(12);
		this.mensaje3.setBuzonRemitente(this.buzon3);
		this.mensaje3.setBuzonDestinatario(this.buzon4);
		this.mensaje4 = new Mensaje();
		this.mensaje4.setId(13);
		this.mensaje4.setBuzonRemitente(this.buzon4);
		this.mensaje4.setBuzonDestinatario(this.buzon);

		Collection<Mensaje> mensajes = new ArrayList<Mensaje>();
		Collection<Mensaje> mensajes2 = new ArrayList<Mensaje>();
		mensajes.add(this.mensaje4);
		mensajes2.add(this.mensaje2);

		BDDMockito.given(this.mensajeService.findByBuzonDestinatarioId(10)).willReturn(mensajes);
		BDDMockito.given(this.mensajeService.findByBuzonRemitenteId(10)).willReturn(mensajes2);
		BDDMockito.given(this.mensajeService.findByBuzonDestinatarioId(11)).willReturn(mensajes);
		BDDMockito.given(this.mensajeService.findByBuzonRemitenteId(11)).willReturn(mensajes2);
		BDDMockito.given(this.mensajeService.findByBuzonDestinatarioId(12)).willReturn(mensajes);
		BDDMockito.given(this.mensajeService.findByBuzonRemitenteId(12)).willReturn(mensajes2);
		BDDMockito.given(this.mensajeService.findByBuzonDestinatarioId(13)).willReturn(mensajes);
		BDDMockito.given(this.mensajeService.findByBuzonRemitenteId(13)).willReturn(mensajes2);
		BDDMockito.given(this.mensajeService.findById(10)).willReturn(this.mensaje);

	}

	//Ver mis mensajes
	@WithMockUser(value = "patrocinador")
	@Test
	@DisplayName("Test para peticion GET de los mensajes de un patrocinador")
	void testVerMisMensajesPatrocinador() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeExists("enviados")).andExpect(MockMvcResultMatchers.model().attributeExists("recibidos"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("mensajes/listaMensajes"));
	}

	@WithMockUser(value = "patrocinador4")
	@Test
	@DisplayName("Test Negativo para peticion GET de los mensajes de un patrocinador que no existe")
	void testNegativoVerMisMensajesPatrocinador() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("mensajes")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "propietario")
	@Test
	@DisplayName("Test para peticion GET de los mensajes de un propietario")
	void testVerMisMensajesPropietario() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeExists("enviados")).andExpect(MockMvcResultMatchers.model().attributeExists("recibidos"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("mensajes/listaMensajes"));
	}

	@WithMockUser(value = "propietario3")
	@Test
	@DisplayName("Test Negativo para peticion GET de los mensajes de un propietario que no existe")
	void testNegativoVerMisMensajesPropietario() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("mensajes")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "cliente")
	@Test
	@DisplayName("Test para peticion GET de los mensajes de un cliente")
	void testVerMisMensajesCliente() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeExists("enviados")).andExpect(MockMvcResultMatchers.model().attributeExists("recibidos"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("mensajes/listaMensajes"));
	}

	@WithMockUser(value = "cliente3")
	@Test
	@DisplayName("Test Negativo para peticion GET de los mensajes de un cliente que no existe")
	void testNegativoVerMisMensajesCliente() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("mensajes")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "administrador")
	@Test
	@DisplayName("Test para peticion GET de los mensajes de un administrador")
	void testVerMisMensajesAdministrador() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeExists("enviados")).andExpect(MockMvcResultMatchers.model().attributeExists("recibidos"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("mensajes/listaMensajes"));
	}

	@WithMockUser(value = "administrador3")
	@Test
	@DisplayName("Test Negativo para peticion GET de los mensajes de un administrador que no existe")
	void testNegativoVerMisMensajesAdministrador() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("mensajes")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	//Ver detalles de un mensaje

	@WithMockUser(value = "patrocinador")
	@Test
	@DisplayName("Test para peticion GET de los detalles de un mensaje ")
	void testDetallesMensaje() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/mensajes/10")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("mensajes/mensajeDetails"));
	}

	@WithMockUser(value = "patrocinador")
	@Test
	@DisplayName("Test Negativo para peticion GET de los detalles de un mensaje cuyo id no existe")
	void testNegativoDetallesMensaje() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/mensajes/15")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}
}
