
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
public class MensajeControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;


	//Ver mis mensajes
	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test para peticion GET de los mensajes de un patrocinador")
	void testVerMisMensajesPatrocinador() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeExists("enviados")).andExpect(MockMvcResultMatchers.model().attributeExists("recibidos"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("mensajes/listaMensajes"));
	}

	@WithMockUser(username = "patrocinador4", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test Negativo para peticion GET de los mensajes de un patrocinador que no existe")
	void testNegativoVerMisMensajesPatrocinador() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("mensajes")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "propietario1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test para peticion GET de los mensajes de un propietario")
	void testVerMisMensajesPropietario() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeExists("enviados")).andExpect(MockMvcResultMatchers.model().attributeExists("recibidos"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("mensajes/listaMensajes"));
	}

	@WithMockUser(username = "propietario3", authorities = {
		"propietario"
	})
	@Test
	@DisplayName("Test Negativo para peticion GET de los mensajes de un propietario que no existe")
	void testNegativoVerMisMensajesPropietario() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("mensajes")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "cliente1", authorities = {
		"cliente"
	})
	@Test
	@DisplayName("Test para peticion GET de los mensajes de un cliente")
	void testVerMisMensajesCliente() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeExists("enviados")).andExpect(MockMvcResultMatchers.model().attributeExists("recibidos"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("mensajes/listaMensajes"));
	}

	@WithMockUser(username = "cliente3", authorities = {
		"cliente"
	})
	@Test
	@DisplayName("Test Negativo para peticion GET de los mensajes de un cliente que no existe")
	void testNegativoVerMisMensajesCliente() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("mensajes")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "admin", authorities = {
		"admin"
	})
	@Test
	@DisplayName("Test para peticion GET de los mensajes de un administrador")
	void testVerMisMensajesAdministrador() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeExists("enviados")).andExpect(MockMvcResultMatchers.model().attributeExists("recibidos"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("mensajes/listaMensajes"));
	}

	@WithMockUser(username = "administrador", authorities = {
		"admin"
	})
	@Test
	@DisplayName("Test Negativo para peticion GET de los mensajes de un administrador que no existe")
	void testNegativoVerMisMensajesAdministrador() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/usuario/mensajes")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("mensajes")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	//Ver detalles de un mensaje

	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test para peticion GET de los detalles de un mensaje ")
	void testDetallesMensaje() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/mensajes/{mensajeId}", 3))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view()
				.name("mensajes/mensajeDetails"));
	}

	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test Negativo para peticion GET de los detalles de un mensaje cuyo id no existe")
	void testNegativoDetallesMensaje() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/mensajes/{mensajeId}", 15)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}
	// Crear un mensaje
	
		@WithMockUser(username = "patrocinador1", authorities = {
				"patrocinador"
		})
		@Test
		@DisplayName("Test para peticion POST para crear un formulario")
		public void testInitCreationForm() throws Exception {
			this.mockMvc.perform(MockMvcRequestBuilders.get("/mensajes/new"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.model().attributeExists("mensaje"))
					.andExpect(MockMvcResultMatchers.view().name("mensajes/new"));
		}
		
		@WithMockUser(username = "patrocinador1", authorities = {
				"patrocinador"
		})
		@Test
		@DisplayName("Test positivo para peticion POST de crear un mensaje")
		void testCreateMensaje() throws Exception {
			this.mockMvc
			.perform(MockMvcRequestBuilders.post("/mensajes/new").with(SecurityMockMvcRequestPostProcessors.csrf())
					.param("asunto", "asunto prueba").param("cuerpo", "cuerpo prueba")
					.param("destinatario", "administrador"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/usuario/mensajes"));
	}
		
		@WithMockUser(username = "patrocinador1", authorities = {
				"patrocinador"
		})
		@Test
		@DisplayName("Test negativo para peticion POST de crear un mensaje")
		public void testProcessCreationFormHasErrors() throws Exception {
			this.mockMvc
					.perform(MockMvcRequestBuilders.post("/mensajes/new").with(SecurityMockMvcRequestPostProcessors.csrf())
							.param("cuerpo", "cuerpo prueba")
							.param("destinatario", "administrador"))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
					.andExpect(MockMvcResultMatchers.view().name("mensajes/new"));
		}
		
}
