
package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AdminController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AdministratorControllerTests {

	@MockBean
	private AdministradorService administradorService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void datosIniciales() {
		BDDMockito.given(this.administradorService.fiestaAceptado()).willReturn(new Double(20.0));
		BDDMockito.given(this.administradorService.fiestaPendiente()).willReturn(new Double(20.0));
		BDDMockito.given(this.administradorService.fiestaRechazado()).willReturn(new Double(60.0));

		BDDMockito.given(this.administradorService.localAceptado()).willReturn(new Double(20.0));
		BDDMockito.given(this.administradorService.localPendiente()).willReturn(new Double(20.0));
		BDDMockito.given(this.administradorService.localRechazado()).willReturn(new Double(60.0));

		BDDMockito.given(this.administradorService.solicitudAceptado()).willReturn(new Double(20.0));
		BDDMockito.given(this.administradorService.solicitudPendiente()).willReturn(new Double(20.0));
		BDDMockito.given(this.administradorService.solicitudRechazado()).willReturn(new Double(60.0));

	}


	@WithMockUser(username = "admin")
	@Test
	@DisplayName("Test para peticion GET del dashboard positivo")
	void testVerDashboardPositivo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrador/dashboard"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("localA"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("localP"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("localR"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("fiestaA"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("fiestaP"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("fiestaR"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("solicitudA"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("solicitudP"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("solicitudR"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("admin/dashboard"));
	}
	
	@Test
	@DisplayName("Test para peticion GET del dashboard negativo")
	void testVerDashboardNegativo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrador/dashboard"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
