
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.LinkedList;

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
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.ValoracionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = LocalController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class LocalControllerTests {

	@MockBean
	private LocalService		localService;

	@MockBean
	private ComentarioService	comentarioService;

	@MockBean
	private ValoracionService	valoracionService;

	@MockBean
	private FiestaService		fiestaService;

	@MockBean
	private AnuncioService		anuncioService;

	@MockBean
	private PropietarioService	propietarioService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Propietario			george;

	private Local				l3;

	private Local				l4;

	private Local				local1	= new Local();

	private Local				local2	= new Local();


	@BeforeEach
	void datosIniciales() {
		this.george = new Propietario();
		this.george.setApellidos("Apellidos prueba");
		this.george.setEmail("email@prueba.es");
		this.george.setFoto("http://url.com");
		this.george.setId(10);
		this.george.setNombre("george");
		this.george.setTelefono("654321987");
		BDDMockito.given(this.propietarioService.findByUsername("george")).willReturn(this.george);
		Local l1 = new Local();
		Local l2 = new Local();
		this.l3 = new Local();
		this.l3.setDecision("PENDING");
		this.l3.setDireccion("direccion");
		this.l3.setId(20);
		this.l4 = new Local();
		this.l4.setDecision("PENDING");
		this.l4.setId(21);
		Collection<Local> locales = new LinkedList<Local>();
		locales.add(l1);
		locales.add(l2);
		locales.add(this.l3);
		locales.add(this.l3);
		BDDMockito.given(this.localService.findByPropietarioId(this.george.getId())).willReturn(locales);
		Local mockDenegado = this.l3;
		Local mockAceptado = this.l3;
		mockDenegado.setDecision("RECHAZADO");
		mockAceptado.setDecision("ACEPTADO");
		BDDMockito.given(this.localService.denegarSolicitudLocal(this.l3.getId())).willReturn(mockDenegado);
		BDDMockito.given(this.localService.aceptarSolicitudLocal(this.l3.getId())).willReturn(mockDenegado);
		BDDMockito.given(this.localService.findLocalById(this.l3.getId())).willReturn(this.l3);

		this.local1.setDireccion("Luis Montoto 30");
		this.local1.setId(10);
		BDDMockito.given(this.localService.findLocalById(10)).willReturn(this.local1);

		Collection<Local> localesLuisMontoto = new LinkedList<Local>();
		localesLuisMontoto.add(this.local1);
		BDDMockito.given(this.localService.findByDireccion("Luis Montoto")).willReturn(localesLuisMontoto);

		locales.add(this.local1);
		locales.add(this.local2);
		BDDMockito.given(this.localService.findByPropietarioId(this.george.getId())).willReturn(locales);
	}

	//---------------Crear local (propietario)----------------------------------------------------------------------------------------

	@WithMockUser(value = "bob")
	@Test
	@DisplayName("Test para peticion POST para crear un formulario")
	public void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("local"))
			.andExpect(MockMvcResultMatchers.view().name("locales/createLocalForm"));
	}

	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion POST para registrar un local")
	public void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/locales/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("direccion", "Direccion prueba").param("capacidad", "1765").param("condiciones", "Condiciones prueba").param("imagen",
			"https://bangbranding.com/blog/wp-content/uploads/2016/11/700x511_SliderInterior.jpg")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/propietario/locales"));
	}

	@WithMockUser(value = "bob")
	@Test
	@DisplayName("Test negativo para peticion POST para registrar un local")
	public void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/locales/new").param("direccion", "Direccion Prueba").param("capacidad", "1765").param("condiciones", "Condiciones prueba")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//-----------------Detalles local (todos)----------------------------------------------------------------------------

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET de los detalles del local ")
	void testDetallesLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/{localId}", this.local1.getId())).andExpect(MockMvcResultMatchers.model().attributeExists("valoraciones")).andExpect(MockMvcResultMatchers.model().attributeExists("comentarios"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("locales/localDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test negativo para peticion GET de los detalles de un local cuyo id no existe")
	void testNegativoDetallesLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/{localId}", 50)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	//-------------------Buscar locales (todos)------------------------------------------------------------------

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET del formulario de busqueda de locales")
	void testFormularioBusquedaLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/buscar")).andExpect(MockMvcResultMatchers.model().attributeExists("local")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("locales/buscarLocales"));
	}

	@DisplayName("Test para peticion GET del resultado del formulario de busqueda de locales")
	void testResultadoFormularioBusquedaLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales").param("direccion", "Luis Montoto")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("locales/listaLocales"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test negativo para peticion GET del resultado del formulario de busqueda de locales")
	void testResultadoFormularioBusquedaLocalesNegativo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales").param("direccion", "Probando")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("locales")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("locales/buscarLocales"));
	}

	//--------------------------Mis locales (propietario)------------------------------------------------------------------------------------------

	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion GET de los locales de un propietario logeado")
	void testVerMisLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/locales")).andExpect(MockMvcResultMatchers.model().attributeExists("locales")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("locales/listaLocales"));
	}

	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para peticion GET de los locales de un propietario que no existe")
	void testNegativoVerMisLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/locales")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("locales")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "admin", roles = {
		"cliente", "admin"
	})
	@Test
	@DisplayName("Test para peticion GET de aceptar locales")
	void testAceptarLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrador/local/{localId}/aceptar", this.l3.getId())).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/administrador/locales"));
	}

	@WithMockUser(username = "admin", roles = {
		"cliente", "admin"
	})
	@Test
	@DisplayName("Test para peticion GET de rechazar locales")
	void testRechazarLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrador/local/{localId}/rechazar", this.l3.getId())).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/administrador/locales"));
	}

}
