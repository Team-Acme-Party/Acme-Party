
package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.SolicitudAsistenciaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FiestaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ComentarioControllerTests {

	@MockBean
	private FiestaService fiestaService;

	@MockBean
	private ClienteService clienteService;

	@MockBean
	private LocalService localService;

	@MockBean
	private ComentarioService comentarioService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private SolicitudAsistenciaService solicitudAsistenciaService;

	@Autowired
	private MockMvc mockMvc;

	private Cliente cliente;

	private Cliente cliente2;

	private Fiesta fiesta1 = new Fiesta();

	private Local local1 = new Local();

	private SolicitudAsistencia solicitudAisitencia1 = new SolicitudAsistencia();


	@BeforeEach
	void datosCliente() {
		this.cliente = new Cliente();
		this.cliente.setApellidos("Apellidos prueba");
		this.cliente.setDescripcionGustos("Gustos de prueba");
		this.cliente.setEmail("email@prueba.es");
		this.cliente.setFoto("http:url.com");
		this.cliente.setId(10);
		this.cliente.setNombre("cli");
		this.cliente.setTelefono("654321987");
		BDDMockito.given(this.clienteService.findByUsername("cliente")).willReturn(this.cliente);

		this.cliente2 = new Cliente();
		this.cliente2.setApellidos("Apellidos prueba 2");
		this.cliente2.setDescripcionGustos("Gustos de prueba 2");
		this.cliente2.setEmail("email2@prueba.es");
		this.cliente2.setFoto("http:url.com");
		this.cliente2.setId(11);
		this.cliente2.setNombre("cli2");
		this.cliente2.setTelefono("654321987");
		BDDMockito.given(this.clienteService.findByUsername("cliente2")).willReturn(this.cliente2);

		this.local1.setId(3);


		this.fiesta1.setId(3);
		this.fiesta1.setLocal(local1);

		this.solicitudAisitencia1.setCliente(cliente);
		this.solicitudAisitencia1.setFiesta(fiesta1);
		this.solicitudAisitencia1.setDecision("ACEPTADO");



		BDDMockito.given(this.fiestaService.findFiestaById(3)).willReturn(fiesta1);
		BDDMockito.given(this.localService.findLocalById(3)).willReturn(this.local1);
	}

//	@WithMockUser(value = "cliente")
//	@Test
//	@DisplayName("Test positivo crear comentario de cliente con asistencia al local")
//	void testPositivoComentarioLocal() throws Exception {
//		// TODO
//		this.mockMvc.perform(MockMvcRequestBuilders.post("/comentario/new/local/{localId}", this.local1.getId()).
//	}
//
//
//
//	@WithMockUser(value = "cliente2")
//	@Test
//	@DisplayName("Test negativo crear comentario de cliente con asistencia al local")
//	void testNegativoComentarioLocal() throws Exception {
//		// TODO
//		this.mockMvc
//				.perform(MockMvcRequestBuilders.post("/comentario/new/local/{localId}", this.local1.getId())
//	}
//
//}
//
//	@WithMockUser(value = "cliente")
//	@Test
//	@DisplayName("Test positivo crear comentario de cliente con asistencia a la fiesta")
//	void testPositivoComentarioFiesta() throws Exception {
//		// TODO
//		this.mockMvc
//				.perform(MockMvcRequestBuilders.post("/comentario/new/fiesta/{fiestalId}", this.fiesta1.getId())
//	}
//
//}
//
//	@WithMockUser(value = "cliente2")
//	@Test
//	@DisplayName("Test negativo crear comentario de cliente con asistencia a la fiesta")
//	void testNegativoComentarioFiesta() throws Exception {
//		// TODO
//		this.mockMvc
//				.perform(MockMvcRequestBuilders.post("/comentario/new/fiesta/{fiestaId}", this.fiesta1.getId())
//	}

}
