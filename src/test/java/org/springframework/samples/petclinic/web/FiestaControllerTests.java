
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
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
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

	private Cliente				george;

	private Fiesta				test	= new Fiesta();


	@BeforeEach
	void datosCliente() {
		this.george = new Cliente();
		this.george.setApellidos("Apellidos prueba");
		this.george.setDescripcionGustos("Gustos de prueba");
		this.george.setEmail("email@prueba.es");
		this.george.setFoto("http:url.com");
		this.george.setId(10);
		this.george.setNombre("geoge");
		this.george.setTelefono("654321987");
		BDDMockito.given(this.clienteService.findByUsername("george")).willReturn(this.george);

		Fiesta fiesta1 = new Fiesta();
		Fiesta fiesta2 = new Fiesta();
		fiesta1.setNombre("Fiesta de disfraces 1");
		fiesta2.setNombre("Fiesta de disfraces 2");
		Collection<Fiesta> fiestas1 = new LinkedList<Fiesta>();
		Collection<Fiesta> fiestas2 = new LinkedList<Fiesta>();
		fiestas1.add(fiesta1);
		fiestas1.add(fiesta2);
		fiestas2.add(this.test);
		this.test.setNombre("disfraces");
		BDDMockito.given(this.fiestaService.findByClienteId(this.george.getId())).willReturn(fiestas1);
		BDDMockito.given(this.fiestaService.findByNombre(this.test.getNombre())).willReturn(fiestas2);
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

	@WithMockUser(value = "george")
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
}
