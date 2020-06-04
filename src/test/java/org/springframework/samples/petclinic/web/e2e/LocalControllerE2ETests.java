
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
public class LocalControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	private static final Integer LOCAL_ID = 1;
	private static final Integer LOCAL_FALSO_ID = 50;
	private static final Integer LOCAL_PENDIENTE_LOCAL_ID = 5;

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test para peticion GET de los locales de un propietario")
	void testVerMisAnuncios() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/locales"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("locales"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("mislocales"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("locales/listaLocales"));
	}

	@WithMockUser(username = "propietario50", authorities = { "propietario" })
	@Test
	@DisplayName("Test Negativo para peticion GET de los locales de un propietario que no existe")
	void testNegativoVerMisLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/locales"))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("locales"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test para peticion GET de los detalles del local ")
	void testDetallesLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/{localId}", LOCAL_ID))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("locales/localDetails"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("valoraciones"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("comentarios"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("anuncios"));
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test Negativo para peticion GET de los detalles de un local no aceptado")
	void testNegativoDetallesLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/{localId}", LOCAL_PENDIENTE_LOCAL_ID))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("exception"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("message"));
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test Negativo para peticion GET de los detalles de un local que no existe")
	void testNegativoDetallesLocal2() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/{localId}", LOCAL_FALSO_ID))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test para peticion GET del formulario de registro de un local")
	void testFormularioLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/new"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("local"))
				.andExpect(MockMvcResultMatchers.view().name("locales/createLocalForm"));
	}

	@WithMockUser(username = "propietario50", authorities = { "propietario" })
	@Test
	@DisplayName("Test negativo para peticion GET del formulario de registro de un local (no existe el propietario)")
	void testNegativoFormularioLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/new"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("local"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test para peticion POST del formulario de registro de un local")
	void testNewLocal() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/locales/new").with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("direccion", "direccion de prueba 1").param("capacidad", "500")
						.param("condiciones", "condiciones de prueba 1").param("imagen",
								"https://bangbranding.com/blog/wp-content/uploads/2016/11/700x511_SliderInterior.jpg"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/propietario/locales"));
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test negativo para peticion POST del formulario de registro de un local (falta un parametro)")
	void testNegativoNewLocal() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/locales/new").with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("direccion", "direccion de prueba 1").param("capacidad", "500")
						.param("condiciones", "condiciones de prueba 1"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("locales/createLocalForm"));
	}

	@WithMockUser(username = "admin", authorities = { "admin" })
	@Test
	@DisplayName("Test aceptar solicitud del local")
	void testAceptarSolicitudLocal() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/administrador/local/{localId}/aceptar", LOCAL_PENDIENTE_LOCAL_ID))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/administrador/locales"));
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test negativo aceptar solicitud de un local (rol incorrecto)")
	void testNegativoAceptarLocal() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/administrador/local/{localId}/aceptar", LOCAL_PENDIENTE_LOCAL_ID))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "admin", authorities = { "admin" })
	@Test
	@DisplayName("Test aceptar solicitud del local")
	void testRechazarSolicitudLocal() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.get("/administrador/local/{localId}/rechazar", LOCAL_PENDIENTE_LOCAL_ID))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/administrador/locales"));
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test negativo aceptar solicitud de un local (rol incorrecto)")
	void testNegativoRechazarSolicitudLocal() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.get("/administrador/local/{localId}/rechazar", LOCAL_PENDIENTE_LOCAL_ID))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "propietario1", authorities = { "propietario" })
	@Test
	@DisplayName("Test para peticion GET del formulario de busqueda de un local")
	void testFormularioBuscarLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/buscar"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("local"))
				.andExpect(MockMvcResultMatchers.view().name("locales/buscarLocales"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET del resultado del formulario de busqueda de locales")
	void testResultadoFormularioBusquedaLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales").param("direccion", "Luis Montoto"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("locales/listaLocales"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test negativo para peticion GET del resultado del formulario de busqueda de locales")
	void testResultadoFormularioBusquedaLocalesNegativo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales").param("direccion", "Probando"))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("locales"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.view().name("locales/buscarLocales"));
	}

}
