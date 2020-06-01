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
public class SolicitudControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test para peticion GET de las asistencias")
	void testVerMisAnuncios() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudesAsistencias"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("asistencias"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("solicitudesAsistencia/listaSolicitudesAsistencia"));
	}
	
	@WithMockUser(username = "cliente5", authorities = { "cliente" })
	@Test
	@DisplayName("Test negativo para peticion GET de las asistencias de un usuario que no existe")
	void testNegativoVerMisAnuncios() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudesAsistencias"))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("asistencias"));				
	}
	
	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test negativo para peticion GET para solicitar una asistencia de una fiesa que no existe")
	void testNegativoSolicitudAsistencia() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/fiesta/{fiestaId}",3))		
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}
	
	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test para peticion GET para aceptar una asistencia")
	void testAceptarSolicitudAsistencia() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/aceptar/{solicitudId}",2))		
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/cliente/fiestas"));
	}
	
	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test negativo para peticion GET para aceptar una asistencia con un usuario incorrecto")
	void testNegativoAceptarSolicitudAsistencia() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/aceptar/{solicitudId}",3))		
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test para peticion GET para rechazar una asistencia")
	void testDenegarSolicitudAsistencia() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/rechazar/{solicitudId}",2))		
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/cliente/fiestas"));
	}
	
	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test negativo para peticion GET para rechazar una asistencia con un usuario incorrecto")
	void testNegativoDenegarSolicitudAsistencia() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/solicitudAsistencia/rechazar/{solicitudId}",3))		
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
