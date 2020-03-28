
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
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
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = LocalController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class LocalControllerTests {

	@MockBean
	private LocalService		localService;

	@MockBean
	private FiestaService		fiestaService;

	@MockBean
	private PropietarioService	propietarioService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;
  
  private Propietario			george;

	private Local				test	= new Local();

	private Local				local1 = new Local();

	private Local				local2 = new Local();

	@BeforeEach
	void datosPropietario() {
		this.george = new Propietario();
		this.george.setApellidos("Apellidos prueba");
		this.george.setEmail("email@prueba.es");
		this.george.setFoto("http://url.com");
		this.george.setId(10);
		this.george.setNombre("george");
		this.george.setTelefono("654321987");
		BDDMockito.given(this.propietarioService.findByUsername("george")).willReturn(this.george);
    
    Collection<Local> locales = new ArrayList<Local>();
		Collection<Fiesta> fiestas = new ArrayList<Fiesta>();
		Local localA = new Local();
		Local localB = new Local();
		Fiesta fiestaA = new Fiesta();
		Fiesta fiestaB = new Fiesta();
		locales.add(localA);
		locales.add(localB);
		fiestas.add(fiestaA);
		fiestas.add(fiestaB);
    BDDMockito.given(this.localService.findAccepted()).willReturn(locales);
		BDDMockito.given(this.fiestaService.findAccepted()).willReturn(fiestas);
    
		Local l1 = new Local();
		Local l2 = new Local();
		Collection<Local> locales = new LinkedList<Local>();
		locales.add(l1);
		locales.add(l2);
		BDDMockito.given(this.localService.findByPropietarioId(this.george.getId())).willReturn(locales);
		Collection<Local> locales2 = new LinkedList<Local>();
		this.test.setDireccion("Luis Montoto");
		locales2.add(this.test);
		BDDMockito.given(this.localService.findByDireccion(this.test.getDireccion())).willReturn(locales2);
	}

  @WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET de los locales registrados aceptados por cualquier usuario")
	public void testWelcome() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("fiestas")).andExpect(MockMvcResultMatchers.model().attributeExists("locales"))
			.andExpect(MockMvcResultMatchers.view().name("welcome"));
  }
  
	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET del formulario de busqueda de locales")
	void testFormularioBusquedaLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/buscar")).andExpect(MockMvcResultMatchers.model().attributeExists("local")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("locales/buscarLocales"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test negativo para peticion GET de los locales registrados aceptados por cualquier usuario")
	public void testWelcomeNegativo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("fiestas"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("locales")).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	//CREAR LOCAL POR UN PROPIETARIO

	@WithMockUser(value = "bob")
	@Test
	@DisplayName("Test para peticion POST para crear un formulario")
	public void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("local"))
			.andExpect(MockMvcResultMatchers.view().name("locales/createLocalForm"));
	}

	@WithMockUser(value = "bob")
	@Test
	@DisplayName("Test para peticion POST para registrar un local")
	public void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/locales/new")
			//			.with(csrf())
			.param("direccion", "Direccion prueba").param("capacidad", "1765").param("condiciones", "Condiciones prueba").param("imagen", "https://bangbranding.com/blog/wp-content/uploads/2016/11/700x511_SliderInterior.jpg"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "bob")
	@Test
	@DisplayName("Test negativo para peticion POST para registrar un local")
	public void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/locales/new").param("direccion", "Direccion Prueba").param("capacidad", "1765").param("condiciones", "Condiciones prueba")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("imagen")).andExpect(MockMvcResultMatchers.view().name("locales/createLocalForm"));
	}

	//MOSTRAR DETALLES DE LOCALES Y FIESTAS

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET de los detalles del local ")
	void testDetallesAnuncio() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/{localId}", this.local1.getId())).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("locales/localDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test Negativo para peticion GET de los detalles de un local cuyo id no existe")
  void testNegativoDetallesAnuncio() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/locales/{localId}", this.a2.getId())).andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

    @DisplayName("Test para peticion GET del resultado del formulario de busqueda de locales")
	void testResultadoFormularioBusquedaLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales").param("direccion", "Luis Montoto")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("locales/listaLocales"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET del resultado del formulario de busqueda de locales")
	void testResultadoFormularioBusquedaLocalesNegativo() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/locales", this.test)).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("locales")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("locales/buscarLocales"));
	}

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

}
