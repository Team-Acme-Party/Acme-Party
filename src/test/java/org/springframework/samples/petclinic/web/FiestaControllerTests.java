package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(controllers = FiestaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class FiestaControllerTests {

	@Autowired
	private FiestaController fiestaController;
	
	@MockBean
	private FiestaService fiestaService;
	
	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private LocalService localService;
	
	@MockBean
	private PropietarioService propietarioService;
	
	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;
	
	private Cliente george;
	
	@BeforeEach
	void datosCliente() {
		george = new Cliente();
		george.setApellidos("Apellidos prueba");
		george.setDescripcionGustos("Gustos de prueba");
		george.setEmail("email@prueba.es");
		george.setFoto("http://url.com");
		george.setId(10);
		george.setNombre("geoge");
		george.setTelefono("654321987");
		given(this.clienteService.findByUsername("george")).willReturn(george);
		
		Fiesta fiesta1 = new Fiesta();
		Fiesta fiesta2 = new Fiesta();
		Collection<Fiesta> fiestas = new LinkedList<Fiesta>();
		fiestas.add(fiesta1);
		fiestas.add(fiesta2);
		given(this.fiestaService.findByClienteId(george.getId())).willReturn(fiestas);
	}
	
	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion GET de las fiestas organizadas por un cliente logeado")
	void testVerMisFiestas() throws Exception {
		mockMvc.perform(get("/cliente/fiestas")).andExpect(model().attributeExists("fiestas"))
				.andExpect(model().attributeExists("misfiestas")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("fiestas/listaFiestas"));
	}
	
	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para peticion GET de las fiestas organizadas por un cliente que no existe")
	void testNegativoVerMisFiestas() throws Exception {
		mockMvc.perform(get("/cliente/fiestas")).andExpect(model().attributeDoesNotExist("fiestas"))
				.andExpect(model().attributeDoesNotExist("misfiestas")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("exception"));
	}
}