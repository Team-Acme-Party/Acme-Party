
package org.springframework.samples.petclinic.web.e2e;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class AdministratorControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "admin", authorities = { "admin" })
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
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

}
