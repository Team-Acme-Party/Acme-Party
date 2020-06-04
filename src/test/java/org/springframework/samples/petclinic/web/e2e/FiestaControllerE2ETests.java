
package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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
public class FiestaControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	private static final Integer FIESTA1_ID = 3;
	private static final Integer FIESTA2_ID = 4;
	private static final Integer FIESTA_FALSO_ID = 21;

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test para peticion GET del formulario de busqueda de fiestas")
	void testFormularioBusquedaFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas/buscar"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("fiesta"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/buscarFiestas"));
	}

	@WithMockUser(username = "cliente2", authorities = { "cliente" })
	@Test
	@DisplayName("Test para peticion GET del resultado del formulario de busqueda de fiestas")
	void testResultadoFormularioBusquedaFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas").param("nombre", "Fiesta"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/listaFiestas"));
	}

	@WithMockUser(username = "cliente2", authorities = { "cliente" })
	@Test
	@DisplayName("Test negativo para peticion GET del resultado del formulario de busqueda de fiestas")
	void testResultadoFormularioBusquedaFiestasNegativo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas").param("nombre", "Betis"))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("fiestas"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/buscarFiestas"));
	}

	@WithMockUser(username = "cliente2", authorities = { "cliente" })
	@Test
	@DisplayName("Test para peticion GET de los detalles de la fiesta ")
	void testDetallesFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas/{fiestaId}", FiestaControllerE2ETests.FIESTA1_ID))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/fiestaDetails"));
	}

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test negativo para peticion GET de los detalles de una fiesta cuyo id no existe")
	void testNegativoDetallesFiesta() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/fiestas/{fiestaId}", FiestaControllerE2ETests.FIESTA_FALSO_ID))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "patrocinador2", authorities = { "patrocinador" })
	@Test
	@DisplayName("Test negativo para peticion GET de los detalles de una fiesta con un rol incorrecto")
	void testNegativoDetallesFiestaPorRolIncorrecto() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas/{fiestaId}", 2))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test para peticion GET de las fiestas organizadas por un cliente logeado")
	void testVerMisFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/fiestas"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("fiestas"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("misfiestas"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/listaFiestas"));
	}

	@WithMockUser(username = "cliente77", authorities = { "cliente" })
	@Test
	@DisplayName("Test Negativo para peticion GET de las fiestas organizadas por un cliente que no existe")
	void testNegativoVerMisFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/fiestas"))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("fiestas"))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("misfiestas"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "propietario2", authorities = { "propietario" })
	@Test
	@DisplayName("Test Negativo para peticion GET de las fiestas organizadas por un usuario de rol incorrecto")
	void testNegativoVerMisFiestasRolIncorrecto() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/fiestas"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test positivo para editar fiesta con su cliente dueño")
	void testPositivoEditarMisFiestas() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/fiestas/{fiestaId}/editar", FiestaControllerE2ETests.FIESTA2_ID))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/new"));
	}

	@WithMockUser(username = "cliente2", authorities = { "cliente" })
	@Test
	@DisplayName("Test Negativo editar fiesta con otro usuario que no es su dueño")
	void testNegativoEditarFiesta() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/fiestas/{fiestaId}/editar", FiestaControllerE2ETests.FIESTA2_ID))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test Negativo editar fiesta con otro rol que no es el suyo")
	void testNegativoEditarFiestaRolDistinto() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fiestas/1/editar"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("TestPositivoUpdateFiesta")
	void testProcessUpdateFiestaFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fiestas/{fiestaId}/editar", 1)
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("nombre", "Joe").param("descripcion", "Bloggs")
				.param("id", "1").param("precio", "3.3").param("requisitos", "Testing").param("fecha", "2015/05/25")
				.param("horaInicio", "18:20:58.417").param("horaFin", "12:00").param("numeroAsistentes", "12")
				.param("aforo", "50").param("imagen", "https://welcometoibiza.jpg"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/new"));
	}

	@WithMockUser(username = "cliente2", authorities = { "cliente" })
	@Test
	@DisplayName("TestNegativoUpdateFiesta")
	void testPositivoUpdateFiesta() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/fiestas/{fiestasId}/editar", FiestaControllerE2ETests.FIESTA1_ID)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("nombre", "Joe").param("aforo", "50")
						.param("descripcion", "Bloggs").param("precio", "3.3").param("fecha", "2015/05/25"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/new"));
	}

	// Aceptar y rechazar fiestas

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test positivo aceptar fiesta")
	void testPositivoAceptarFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/local/{localId}/fiesta/{fiestaId}/aceptar", 1, 2))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("welcome"));
	}

	@WithMockUser(username = "propietario2", authorities = { "propietario" })
	@Test
	@DisplayName("Test Negativo aceptar fiesta con otro usuario que no es su dueño")
	void testNegativoAceptarFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/local/{localId}/fiesta/{fiestaId}/aceptar", 1, 1))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "cliente2", authorities = { "cliente" })
	@Test
	@DisplayName("Test Negativo aceptar fiesta con otro rol que no le corresponde")
	void testNegativoAceptarFiestaOtroRol() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/local/{localId}/fiesta/{fiestaId}/aceptar", 1, 1))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test positivo denegar fiesta")
	void testPositivoRechazarFiestas() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/local/{localId}/fiesta/{fiestaId}/denegar", 1, 2))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("welcome"));
	}

	@WithMockUser(username = "propietario2", authorities = { "propietario" })
	@Test
	@DisplayName("Test Negativo denegar fiesta con otro usuario que no es su dueño")
	void testNegativoRechazarFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/local/{localId}/fiesta/{fiestaId}/denegar", 1, 1))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test Negativo de rechazar fiesta por rol incorrecto")
	void testNegativoRechazarRolIncorrecto() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/local/{localId}/fiesta/{fiestaId}/denegar", 1, 1))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test para peticion GET de un formulario positivo")
	public void testIniciarFormularioCrearFiestaPostivo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas/new/{localId}", 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/new"));
	}

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test para peticion GET de un formulario negativo - No existe el local")
	public void testIniciarFormularioCrearFiestaNegativoLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fiestas/new/{localId}", 6666))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test negativo para peticion POST para crear una nueva fiesta - URL incorrecto")
	public void testProcessCreationFormWrongURL() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/fiestas/new/{localId}", 1)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("nombre", "nueva fiesta")
						.param("descripcion", "creacion").param("precio", "13,3").param("requisitos", "ninguno")
						.param("aforo", "50").param("imagen", "ddgf"))
				.andExpect(model().attributeHasFieldErrors("fiesta", "imagen"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/new"));
	}

	@WithMockUser(username = "cliente1", authorities = { "cliente" })
	@Test
	@DisplayName("Test negativo para peticion POST para crear una nueva fiesta - Precio incorrecto")
	public void testProcessCreationFormWrongPrice() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/fiestas/new/{localId}", 1)
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("nombre", "nueva fiesta")
				.param("descripcion", "creacion").param("precio", "rerer").param("requisitos", "ninguno")
				.param("aforo", "50").param("imagen",
						"https://4.bp.blogspot.com/-21qlqUcqgT4/VlUSvE8qmVI/AAAAAAAAAEY/N6RA40TcsQ4/s1600/tumblr_inline_mymygb26FE1ruzwds.jpg"))
				.andExpect(model().attributeHasFieldErrors("fiesta", "precio"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("fiestas/new"));
	}

}
