
package org.springframework.samples.petclinic.web.e2e;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class WelcomeControllerE2ETests {

	@Autowired
	private MockMvc				mockMvc;


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
