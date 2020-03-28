
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;

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

	@Autowired
	private LocalController		localController;

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

	private Propietario			bob;

	private Local				local1;

	private Local				local2;


	@BeforeEach
	void datosIniciales() {

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

		this.bob = new Propietario();
		this.bob.setNombre("bobby");
		this.bob.setApellidos("Apellidos prueba");
		this.bob.setEmail("email@prueba.es");
		this.bob.setTelefono("654321987");
		this.bob.setFoto("http://url.com");
		this.bob.setId(10);

		this.local1 = new Local();
		this.local1.setDireccion("Direccion pruebaaaaa");
		this.local1.setCapacidad(21313);
		this.local1.setCondiciones("Ninguna");
		this.local1.setImagen("https://bangbranding.com/blog/wp-content/uploads/2016/11/700x511_SliderInterior.jpg");
		this.local1.setDecision("ACEPTADO");
		this.local1.setPropietario(this.propietarioService.findById(1));

		Collection<Local> localesXD = this.localService.findByPropietarioId(1);
		for (Local l : localesXD) {
			if (l.getDireccion().equals("Direccion pruebaaaaa")) {
				BDDMockito.given(l).willReturn(this.local1);
			}
		}

		BDDMockito.given(this.propietarioService.findByUsername("bob")).willReturn(this.bob);
		BDDMockito.given(this.localService.findAccepted()).willReturn(locales);
		BDDMockito.given(this.fiestaService.findAccepted()).willReturn(fiestas);
	}

	//LISTAR LOCALES Y FIESTAS POR CUALQUIER USUARIO

	@WithMockUser(value = "spring")
	@Test
	@DisplayName("Test para peticion GET de los locales registrados aceptados por cualquier usuario")
	public void testWelcome() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("fiestas")).andExpect(MockMvcResultMatchers.model().attributeExists("locales"))
			.andExpect(MockMvcResultMatchers.view().name("welcome"));
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

}
