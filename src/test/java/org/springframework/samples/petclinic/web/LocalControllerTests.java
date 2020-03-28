
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
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = LocalController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class LocalControllerTests {

	@MockBean
	private LocalService		localService;

	@MockBean
	private FiestaService		fiestaService;

	@MockBean
	private PropietarioService	propietarioService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Propietario			george;

	private Local				test	= new Local();


	@BeforeEach
	void datosPropietario() {
		this.george = new Propietario();
		this.george.setApellidos("Apellidos prueba");
		this.george.setEmail("email@prueba.es");
		this.george.setFoto("http://url.com");
		this.george.setId(10);
		this.george.setNombre("george");
		this.george.setTelefono("654321987");
		BDDMockito.given(this.propietarioService.findByUsername("george")).willReturn(this.george);
		Local l1 = new Local();
		Local l2 = new Local();
		Collection<Local> locales = new LinkedList<Local>();
		locales.add(l1);
		locales.add(l2);
		BDDMockito.given(this.localService.findByPropietarioId(this.george.getId())).willReturn(locales);
		Collection<Local> locales2 = new LinkedList<Local>();
		this.test.setDireccion("Luis Montoto");
		locales2.add(this.test);
		BDDMockito.given(this.localService.findByDireccion(this.test.getDireccion())).willReturn(locales2);
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET del formulario de busqueda de locales")
	void testFormularioBusquedaLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/buscar")).andExpect(MockMvcResultMatchers.model().attributeExists("local")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("locales/buscarLocales"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET del resultado del formulario de busqueda de locales")
	void testResultadoFormularioBusquedaLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales").param("direccion", "Luis Montoto")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("locales/listaLocales"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET del resultado del formulario de busqueda de locales")
	void testResultadoFormularioBusquedaLocalesNegativo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales", this.test)).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("locales")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("locales/buscarLocales"));
	}

	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion GET de los locales de un propietario logeado")
	void testVerMisLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/locales")).andExpect(MockMvcResultMatchers.model().attributeExists("locales")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("locales/listaLocales"));
	}

	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para peticion GET de los locales de un propietario que no existe")
	void testNegativoVerMisLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/locales")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("locales")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

}
