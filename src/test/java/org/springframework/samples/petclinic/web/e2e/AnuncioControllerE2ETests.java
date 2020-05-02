
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
public class AnuncioControllerE2ETests {

	@Autowired
	private MockMvc					mockMvc;

	private static final Integer	ANUNCIO1_ID			= 1;
	private static final Integer	ANUNCIO_FALSO_ID	= 50;
	private static final Integer	ANUNCIO_PENDIENTE_FIESTA_ID	= 4;
	private static final Integer	ANUNCIO_PENDIENTE_LOCAL_ID	= 5;
	private static final Integer	LOCAL1_ID			= 1;
	private static final Integer	FIESTA1_ID			= 1;


	//Ver anuncios
	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test para peticion GET de los anuncios de un patrocinador")
	void testVerMisAnuncios() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patrocinador/anuncios")).andExpect(MockMvcResultMatchers.model().attributeExists("paraFiestas")).andExpect(MockMvcResultMatchers.model().attributeExists("paraLocales"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("anuncios/listaAnuncios"));
	}

	@WithMockUser(username = "patrocinador7", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test Negativo para peticion GET de los locales de un propietario que no existe")
	void testNegativoVerMisLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patrocinador/anuncios")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncios")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test para peticion GET de los detalles del anuncio ")
	void testDetallesAnuncio() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncios/{anuncioId}", ANUNCIO1_ID)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("anuncios/anuncioDetails"));
	}

	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test Negativo para peticion GET de los detalles de un anuncio cuyo id no existe")
	void testNegativoDetallesAnuncio() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncios/{anuncioId}", ANUNCIO_FALSO_ID))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andExpect(MockMvcResultMatchers.model().attributeExists("message"))
		.andExpect(MockMvcResultMatchers.view().name("exception"));
	}
	
	@WithMockUser(username = "patrocinador2", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test Negativo para peticion GET de los detalles de un anuncio no perteneciente al usuario dado")
	void testNegativoDetallesAnuncio2() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncios/{anuncioId}", ANUNCIO1_ID))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andExpect(MockMvcResultMatchers.model().attributeExists("message"))
		.andExpect(MockMvcResultMatchers.view().name("exception"));
	}
	
	//Crear anuncio para local
	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test para peticion GET del formulario de registro de anuncio para local")
	void testFormularioAnuncioForLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/{localId}/local", LOCAL1_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
		.andExpect(MockMvcResultMatchers.view().name("anuncios/new"));
	}
	
	@WithMockUser(username = "patrocinador7", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test negativo para peticion GET del formulario de registro de anuncio para local (no existe el patrocinador)")
	void testNegativoFormularioAnuncioForLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/{localId}/local", LOCAL1_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncio"))
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test para peticion POST del formulario de registro de anuncio para local")
	void testNewAnuncioForLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/{localId}/local", LOCAL1_ID)
		.with(SecurityMockMvcRequestPostProcessors.csrf())
		.param("imagen", "https://bangbranding.com/blog/wp-content/uploads/2016/11/700x511_SliderInterior.jpg"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/patrocinador/anuncios"));
	}

	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test negativo para peticion POST del formulario de registro de anuncio para local (falta un parametro)")
	void testNegativoNewAnuncioForLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/{localId}/local", LOCAL1_ID)
		.with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andExpect(MockMvcResultMatchers.view().name("anuncios/new"));
	}

	//Crear anuncio para fiesta
	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test para peticion GET del formulario de registro de anuncio para fiesta")
	void testFormularioAnuncioForFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/{fiestaId}/fiesta", FIESTA1_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
		.andExpect(MockMvcResultMatchers.view().name("anuncios/new"));
	}
	
	@WithMockUser(username = "patrocinador7", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test negativo para peticion GET del formulario de registro de anuncio para fiesta (no existe el patrocinador)")
	void testNegativoFormularioAnuncioForFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/{fiestaId}/fiesta", FIESTA1_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncio"))
		.andExpect(MockMvcResultMatchers.view().name("exception"));
	}
	
	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test para peticion POST del formulario de registro de anuncio para fiesta")
	void testNewAnuncioForFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/{fiestaId}/fiesta", FIESTA1_ID)
		.with(SecurityMockMvcRequestPostProcessors.csrf())
		.param("imagen", "https://bangbranding.com/blog/wp-content/uploads/2016/11/700x511_SliderInterior.jpg"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/patrocinador/anuncios"));
	}

	@WithMockUser(username = "patrocinador1", authorities = {
		"patrocinador"
	})
	@Test
	@DisplayName("Test negativo para peticion POST del formulario de registro de anuncio para fiesta (falta un parametro)")
	void testNegativoNewAnuncioForFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/{fiestaId}/fiesta", FIESTA1_ID)
		.with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andExpect(MockMvcResultMatchers.view().name("anuncios/new"));
	}
	
	//Aceptar y rechazar anuncio para local
	@WithMockUser(username = "propietario1", authorities = {
		"propietario"
	})
	@Test
	@DisplayName("Test aceptar anuncio para local")
	void testAceptarAnuncioParaLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/anuncio/{anuncioId}/aceptar", ANUNCIO_PENDIENTE_LOCAL_ID))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/propietario/anuncios"));
	}

	@WithMockUser(username = "propietario2", authorities = {
		"propietario"
	})
	@Test
	@DisplayName("Test negativo aceptar anuncio para local (propietario incorrecto)")
	void testNegativoAceptarAnuncioParaLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/anuncio/{anuncioId}/aceptar", ANUNCIO_PENDIENTE_LOCAL_ID))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "propietario1", authorities = {
		"propietario"
	})
	@Test
	@DisplayName("Test rechazar anuncio para local")
	void testRechazarAnuncioParaLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/anuncio/{anuncioId}/rechazar", ANUNCIO_PENDIENTE_LOCAL_ID))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/propietario/anuncios"));
	}

	@WithMockUser(username = "propietario2", authorities = {
		"propietario"
	})
	@Test
	@DisplayName("Test negativo rechazar anuncio para local (propietario incorrecto)")
	void testNegativoRechazarAnuncioParaLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/anuncio/{anuncioId}/rechazar", ANUNCIO_PENDIENTE_LOCAL_ID))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andExpect(MockMvcResultMatchers.view().name("exception"));
	}
	//	//Aceptar y rechazar anuncio para fiesta
	@WithMockUser(username = "cliente1", authorities = {
		"cliente"
	})
	@Test
	@DisplayName("Test aceptar anuncio para fiesta")
	void testAceptarAnuncioParaFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/anuncio/{anuncioId}/aceptar", ANUNCIO_PENDIENTE_FIESTA_ID))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/cliente/anuncios"));
	}

	@WithMockUser(username = "cliente2", authorities = {
		"cliente"
	})
	@Test
	@DisplayName("Test negativo aceptar anuncio para fiesta (cliente incorrecto)")
	void testNegativoAceptarAnuncioParaFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/anuncio/{anuncioId}/aceptar", ANUNCIO_PENDIENTE_FIESTA_ID))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andExpect(MockMvcResultMatchers.view().name("exception"));
	}
	
	@WithMockUser(username = "cliente1", authorities = {
		"cliente"
	})
	@Test
	@DisplayName("Test rechazar anuncio para fiesta")
	void testRechazarAnuncioParaFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/anuncio/{anuncioId}/rechazar", ANUNCIO_PENDIENTE_FIESTA_ID))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.view().name("redirect:/cliente/anuncios"));
	}

	@WithMockUser(username = "cliente2", authorities = {
		"cliente"
	})
	@Test
	@DisplayName("Test negativo rechazar anuncio para fiesta (cliente incorrecto)")
	void testNegativoRechazarAnuncioParaFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/anuncio/{anuncioId}/rechazar", ANUNCIO_PENDIENTE_FIESTA_ID))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andExpect(MockMvcResultMatchers.view().name("exception"));
	}
}
