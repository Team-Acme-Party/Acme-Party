
package org.springframework.samples.petclinic.web;

import java.time.LocalTime;
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
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = FiestaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class FiestaControllerTests {

	@MockBean
	private FiestaService		fiestaService;

	@MockBean
	private ClienteService		clienteService;

	@MockBean
	private LocalService		localService;

	@MockBean
	private PropietarioService	propietarioService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Cliente				cliente;

	private Propietario			propietario;

	private Fiesta				test	= new Fiesta();

	private Local				local	= new Local();


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

		this.propietario = new Propietario();
		this.propietario.setApellidos("Apellidos prueba");
		this.propietario.setEmail("email@prueba.es");
		this.propietario.setFoto("http:url.com");
		this.propietario.setId(10);
		this.propietario.setNombre("prop");
		this.propietario.setTelefono("654321987");
		BDDMockito.given(this.propietarioService.findByUsername("propietario")).willReturn(this.propietario);

		this.local.setId(3);
		this.local.setPropietario(this.propietario);
		Fiesta fiesta1 = new Fiesta();
		fiesta1.setId(3);
		Fiesta fiesta2 = new Fiesta();
		fiesta1.setNombre("Fiesta de disfraces 1");
		fiesta2.setNombre("Fiesta de disfraces 2");
		Collection<Fiesta> fiestas1 = new LinkedList<Fiesta>();
		Collection<Fiesta> fiestas2 = new LinkedList<Fiesta>();
		fiestas1.add(fiesta1);
		fiestas1.add(fiesta2);
		fiestas2.add(this.test);
		this.test.setNombre("disfraces");
		BDDMockito.given(this.fiestaService.findByClienteId(this.cliente.getId())).willReturn(fiestas1);
		BDDMockito.given(this.fiestaService.findByNombre(this.test.getNombre())).willReturn(fiestas2);
		BDDMockito.given(this.fiestaService.findFiestaById(3)).willReturn(fiesta1);
		BDDMockito.given(this.fiestaService.findFiestaById(1)).willReturn(fiesta1);
		BDDMockito.given(this.localService.findLocalById(3)).willReturn(this.local);
		BDDMockito.given(this.fiestaService.findFiestasByLocalId(3)).willReturn(fiestas1);
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET del formulario de busqueda de fiestas")
	void testFormularioBusquedaFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas/buscar")).andExpect(MockMvcResultMatchers.model().attributeExists("fiesta")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("fiestas/buscarFiestas"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET del resultado del formulario de busqueda de fiestas")
	void testResultadoFormularioBusquedaFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas").param("nombre", "disfraces")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("fiestas/listaFiestas"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET del resultado del formulario de busqueda de fiestas")
	void testResultadoFormularioBusquedaFiestasNegativo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas", this.test)).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("fiestas")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("fiestas/buscarFiestas"));
	}

	@WithMockUser(value = "cliente")
	@Test
	@DisplayName("Test para peticion GET de las fiestas organizadas por un cliente logeado")
	void testVerMisFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/fiestas")).andExpect(MockMvcResultMatchers.model().attributeExists("fiestas")).andExpect(MockMvcResultMatchers.model().attributeExists("misfiestas"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("fiestas/listaFiestas"));
	}

	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para peticion GET de las fiestas organizadas por un cliente que no existe")
	void testNegativoVerMisFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/fiestas")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("fiestas")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("misfiestas"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	// EDITAR
	@WithMockUser(value = "cliente")
	@Test
	@DisplayName("Test positivo editar fiesta con un el cliente dueño")
	void testPositivoEditarMisFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas/{fiestaId}/editar", 1)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("fiestas/new"));
	}

	@WithMockUser(value = "propietario")
	@Test
	@DisplayName("Test Negativo editar fiesta con otro usuario que no es su dueño")
	void testNegativoEditarFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fiesta/{fiestaId}/editar", 1));
	}

//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessUpdateFiestaFormSuccess() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.post("/fiestas/{fiestaId}/editar", 1)
//				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("nombre", "Joe").param("descripcion", "Bloggs")
//				.param("id", "1").param("precio", "3.3").param("requisitos", "Testing").param("fecha", "2015/05/25")
////				.param("horaInicio", "23:00:00")
////				.param("horaFin",  "12:00:00")
//				.param("numeroAsistentes", "12").param("imagen", "https://welcometoibiza.jpg"))
//				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//				.andExpect(MockMvcResultMatchers.view().name("redirect:/fiestas/1"));
//	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFiestaFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fiestas/{fiestasId}/editar", 1)
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("nombre", "Joe")
			.param("descripcion", "Bloggs")
			.param("precio", "3.3")
			.param("fecha", "2015/05/25")
			.param("horaInicio", "23:00")
			.param("horaFin", "14:00"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("fiestas/new"))
			;
	}

	//	@WithMockUser(value = "propietario")
	//	@Test
	//	@DisplayName("Test positivo aceptar fiesta")
	//	void testPositivoAceptarFiestas() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/local/{localId}/fiesta/{fiestaId}/aceptar", 3, 3)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	//	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test Negativo aceptar fiesta con otro usuario que no es su dueño")
	void testNegativoAceptarFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/local/{localId}/fiesta/{fiestaId}/aceptar", 2, 2)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
