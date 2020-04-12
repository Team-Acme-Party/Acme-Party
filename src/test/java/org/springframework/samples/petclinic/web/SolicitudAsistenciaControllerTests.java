package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collection;
import java.util.LinkedList;

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
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.SolicitudAsistenciaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = SolicitudAsistenciaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class SolicitudAsistenciaControllerTests {

	@Autowired
	private SolicitudAsistenciaController solicitudAsistenciaController;

	@MockBean
	private SolicitudAsistenciaService solicitudAsistenciaService;

	@MockBean
	private ClienteService clienteService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Cliente george;

	@BeforeEach
	void datosCliente() {
		george = new Cliente();
		george.setApellidos("Apellidos prueba");
		george.setDescripcionGustos("Gustos de prueba");
		george.setEmail("email@prueba.es");
		george.setFoto("http://url.com");
		george.setId(10);
		george.setNombre("geoge");
		george.setTelefono("654321987");
		SolicitudAsistencia solicitud1 = new SolicitudAsistencia();
		SolicitudAsistencia solicitud2 = new SolicitudAsistencia();
		Fiesta fiesta1 = new Fiesta();
		fiesta1.setId(3);
		fiesta1.setNombre("Fiesta de disfraces 1");
		fiesta1.setCliente(george);
		solicitud1.setFiesta(fiesta1);
		solicitud1.setId(1);
		solicitud1.setCliente(george);
		solicitud1.setDecision("PENDIENTE");
		Collection<SolicitudAsistencia> solicitudes = new LinkedList<SolicitudAsistencia>();
		solicitudes.add(solicitud1);
		solicitudes.add(solicitud2);
		BDDMockito.given(this.clienteService.findByUsername("george")).willReturn(this.george);
		BDDMockito.given(this.solicitudAsistenciaService.findAsistenciasByClienteId(george.getId())).willReturn(solicitudes);
		BDDMockito.given(this.solicitudAsistenciaService.findById(1)).willReturn(solicitud1);
	}

	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion GET de las solicitudes de asistencia de un cliente logeado")
	void testVerMisAsistencias() throws Exception {
		mockMvc.perform(get("/cliente/solicitudesAsistencias")).andExpect(model().attributeExists("asistencias"))
				.andExpect(model().attributeExists("misasistencias")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("solicitudesAsistencia/listaSolicitudesAsistencia"));
	}
	
	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para peticion GET de las solicitudes de asistencia de un cliente que no existe")
	void testNegativoVerMisAsistencias() throws Exception {
		mockMvc.perform(get("/cliente/solicitudesAsistencias")).andExpect(model().attributeDoesNotExist("asistencias"))
				.andExpect(model().attributeDoesNotExist("misasistencias")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("exception"));
	}

	
	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test Positivo para aceptar una solicitud")
	void testPositivoAceptarSolicitud() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/aceptar/{solicitudId}", 1)).andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para aceptar una solicitud, el cliente no puede tomar la decision de esta solicitud")
	void testNegativoAceptarSolicitud() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/aceptar/{solicitudId}", 1)).andExpect(status().is2xxSuccessful()).andExpect(view().name("exception"));
	}
	
	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test Positivo para rechazar una solicitud")
	void testPositivoRechazarSolicitud() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/rechazar/{solicitudId}", 1)).andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para rechazar una solicitud, el cliente no puede tomar la decision de esta solicitud")
	void testNegativoRechazarSolicitud() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/rechazar/{solicitudId}", 1)).andExpect(status().is2xxSuccessful()).andExpect(view().name("exception"));
	}
}
