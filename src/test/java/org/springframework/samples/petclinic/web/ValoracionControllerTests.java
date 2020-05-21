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
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.ValoracionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ValoracionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ValoracionControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ValoracionService valoracionService;

	@MockBean
	private LocalService localService;

	@MockBean
	private ClienteService clienteService;

	@MockBean
	private FiestaService fiestaService;

	private Cliente cliente;

	@BeforeEach
	void datosIniciales() {
		this.cliente = new Cliente();
		this.cliente.setApellidos("Apellidos prueba");
		this.cliente.setEmail("email@prueba.es");
		this.cliente.setFoto("http://url.com/%22");
		this.cliente.setId(10);
		this.cliente.setNombre("cliente");
		this.cliente.setTelefono("654321987");
		BDDMockito.given(this.clienteService.findByUsername("cliente")).willReturn(this.cliente);

	}

	@WithMockUser(value = "cliente")
	@Test
	@DisplayName("Test para peticion POST del formulario de registro de una valoracion para fiesta")
	void testNewComentarioForFiesta() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/valoracion/new/fiesta/1")
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("cuerpo", "comentario"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/fiestas/1"));
	}
	
	@WithMockUser(value = "cliente")
	@Test
	@DisplayName("Test para peticion POST del formulario de registro de una valoracion para local")
	void testNewComentarioForLocal() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/valoracion/new/local/1")
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("cuerpo", "comentario"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/locales/1"));
	}
}
