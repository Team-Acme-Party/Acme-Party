
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
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
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

@WebMvcTest(controllers = WelcomeController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class WelcomeControllerTests {

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


	@BeforeEach
	void datosIniciales() throws Exception {
		Collection<Local> locales = new ArrayList<Local>();
		Collection<Fiesta> fiestas = new ArrayList<Fiesta>();
		Local localA = new Local();
		Local localB = new Local();
		Fiesta fiestaA = new Fiesta();
		Fiesta fiestaB = new Fiesta();
		locales.add(localA);
		locales.add(localB);
		fiestas.add(fiestaA);
		fiestas.add(fiestaB);
		BDDMockito.given(this.localService.findAccepted()).willReturn(locales);
		BDDMockito.given(this.fiestaService.findAccepted()).willReturn(fiestas);
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET de los locales registrados aceptados por cualquier usuario")
	public void testRoot() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("fiestas"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("locales"))
		.andExpect(MockMvcResultMatchers.view().name("welcome"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET de los locales registrados aceptados por cualquier usuario")
	public void testWelcome() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/welcome"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("fiestas"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("locales"))
		.andExpect(MockMvcResultMatchers.view().name("welcome"));
	}

}
