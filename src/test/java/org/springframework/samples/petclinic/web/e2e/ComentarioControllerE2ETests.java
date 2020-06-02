package org.springframework.samples.petclinic.web.e2e;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class ComentarioControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test para peticion POST para crear un comentario sobre una fiesta")
	void testNewAnuncioForLocal() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/comentario/new/fiesta/{fiestaId}", 1)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("cuerpo", "comentario"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/fiestas/1"));
	}

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test negativo para peticion POST para crear un comentario sobre una fiesta (falta un parametro)")
	void testNegativoNewAnuncioForLocal() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/comentario/new/fiesta/{fiestaId}", 1)
						.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/fiestas/1"));
	}
}
