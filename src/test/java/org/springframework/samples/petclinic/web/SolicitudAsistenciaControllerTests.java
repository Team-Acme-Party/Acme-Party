
package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.SolicitudAsistenciaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = SolicitudAsistenciaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class SolicitudAsistenciaControllerTests {

	@MockBean
	private SolicitudAsistenciaService	solicitudAsistenciaService;

	@MockBean
	private ClienteService				clienteService;

	@MockBean
	private FiestaService				fiestaService;

	@MockBean
	private UserService					userService;

	@MockBean
	private AuthoritiesService			authoritiesService;

	@Autowired
	private MockMvc						mockMvc;

	private Cliente						george;


	@BeforeEach
	void datosCliente() {
		this.george = new Cliente();
		this.george.setApellidos("Apellidos prueba");
		this.george.setDescripcionGustos("Gustos de prueba");
		this.george.setEmail("email@prueba.es");
		this.george.setFoto("http://url.com");
		this.george.setId(10);
		this.george.setNombre("geoge");
		this.george.setTelefono("654321987");
		SolicitudAsistencia solicitud1 = new SolicitudAsistencia();
		SolicitudAsistencia solicitud2 = new SolicitudAsistencia();
		Fiesta fiesta1 = new Fiesta();
		fiesta1.setId(3);
		fiesta1.setNombre("Fiesta de disfraces 1");
		fiesta1.setCliente(this.george);
		solicitud1.setFiesta(fiesta1);
		solicitud1.setId(1);
		solicitud1.setCliente(this.george);
		solicitud1.setDecision("PENDIENTE");
		Collection<SolicitudAsistencia> solicitudes = new LinkedList<SolicitudAsistencia>();
		solicitudes.add(solicitud1);
		solicitudes.add(solicitud2);
		BDDMockito.given(this.clienteService.findByUsername("george")).willReturn(this.george);
		BDDMockito.given(this.solicitudAsistenciaService.findAsistenciasByClienteId(this.george.getId())).willReturn(solicitudes);
		BDDMockito.given(this.solicitudAsistenciaService.findById(1)).willReturn(solicitud1);
		BDDMockito.given(this.fiestaService.findFiestaById(3)).willReturn(fiesta1);

	}

	//Ver mis asistencias
	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion GET de las solicitudes de asistencia de un cliente logeado")
	void testVerMisAsistencias() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudesAsistencias")).andExpect(MockMvcResultMatchers.model().attributeExists("asistencias")).andExpect(MockMvcResultMatchers.model().attributeExists("misasistencias"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("solicitudesAsistencia/listaSolicitudesAsistencia"));
	}

	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para peticion GET de las solicitudes de asistencia de un cliente que no existe")
	void testNegativoVerMisAsistencias() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudesAsistencias")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("asistencias")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("misasistencias"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	//Registrar una solicitud
	//	@WithMockUser(value = "george")
	//	@Test
	//	@DisplayName("Test Positivo para registrar una solicitud")
	//	void testPositivoRegistrarSolicitud() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/fiesta/{fiestaId}", 3)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
	//			.andExpect(MockMvcResultMatchers.view().name("solicitudesAsistencia/listaSolicitudesAsistencia"));
	//	}

	//Aceptar una solicitud
	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test Positivo para aceptar una solicitud")
	void testPositivoAceptarSolicitud() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/aceptar/{solicitudId}", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para aceptar una solicitud, el cliente no puede tomar la decision de esta solicitud")
	void testNegativoAceptarSolicitud() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/aceptar/{solicitudId}", 1)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	//Rechazar una solicitud
	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test Positivo para rechazar una solicitud")
	void testPositivoRechazarSolicitud() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/rechazar/{solicitudId}", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para rechazar una solicitud, el cliente no puede tomar la decision de esta solicitud")
	void testNegativoRechazarSolicitud() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/rechazar/{solicitudId}", 1)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}
}
