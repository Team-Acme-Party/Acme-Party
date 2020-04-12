
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
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PatrocinadorService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AnuncioController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AnuncioControllerTests {

	@MockBean
	private AnuncioService		anuncioService;

	@MockBean
	private PatrocinadorService	patrocinadorService;

	@MockBean
	private PropietarioService	propietarioService;

	@MockBean
	private ClienteService		clienteService;

	@MockBean
	private LocalService		localService;

	@MockBean
	private FiestaService		fiestaService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Patrocinador		patrocinador;
	private Propietario			propietario;
	private Propietario			propietario2;
	private Cliente				cliente;
	private Cliente				cliente2;

	private Anuncio				anuncio;
	private Anuncio				a2;


	@BeforeEach
	void datosIniciales() {
		this.patrocinador = new Patrocinador();
		this.patrocinador.setApellidos("Apellidos prueba");
		this.patrocinador.setEmail("email@prueba.es");
		this.patrocinador.setFoto("http://url.com/%22");
		this.patrocinador.setId(10);
		this.patrocinador.setNombre("george");
		this.patrocinador.setTelefono("654321987");
		this.patrocinador.setDescripcionExperiencia("Descipción experiencia");
		BDDMockito.given(this.patrocinadorService.findByUsername("george")).willReturn(this.patrocinador);

		this.propietario = new Propietario();
		this.propietario.setApellidos("Apellidos prueba");
		this.propietario.setEmail("email@prueba.es");
		this.propietario.setFoto("http://url.com/%22");
		this.propietario.setId(10);
		this.propietario.setNombre("propietario");
		this.propietario.setTelefono("654321987");
		BDDMockito.given(this.propietarioService.findByUsername("propietario")).willReturn(this.propietario);

		this.propietario2 = new Propietario();
		this.propietario2.setApellidos("Apellidos prueba");
		this.propietario2.setEmail("email@prueba.es");
		this.propietario2.setFoto("http://url.com/%22");
		this.propietario2.setId(11);
		this.propietario2.setNombre("propietario");
		this.propietario2.setTelefono("654321987");
		BDDMockito.given(this.propietarioService.findByUsername("propietario2")).willReturn(this.propietario2);

		this.cliente = new Cliente();
		this.cliente.setApellidos("Apellidos prueba");
		this.cliente.setEmail("email@prueba.es");
		this.cliente.setFoto("http://url.com/%22");
		this.cliente.setId(10);
		this.cliente.setNombre("cliente");
		this.cliente.setTelefono("654321987");
		BDDMockito.given(this.clienteService.findByUsername("cliente")).willReturn(this.cliente);

		this.cliente2 = new Cliente();
		this.cliente2.setApellidos("Apellidos prueba");
		this.cliente2.setEmail("email@prueba.es");
		this.cliente2.setFoto("http://url.com/%22");
		this.cliente2.setId(11);
		this.cliente2.setNombre("cliente");
		this.cliente2.setTelefono("654321987");
		BDDMockito.given(this.clienteService.findByUsername("cliente2")).willReturn(this.cliente2);

		this.anuncio = new Anuncio();
		this.anuncio.setId(10);
		this.a2 = new Anuncio();
		Collection<Anuncio> anuncios = new LinkedList<Anuncio>();
		anuncios.add(this.anuncio);
		anuncios.add(this.a2);
		BDDMockito.given(this.anuncioService.findByPatrocinadorId(this.patrocinador.getId())).willReturn(anuncios);
		BDDMockito.given(this.anuncioService.findById(10)).willReturn(this.anuncio);
		BDDMockito.given(this.anuncioService.findByPropietarioId(this.propietario.getId())).willReturn(anuncios);
		BDDMockito.given(this.anuncioService.findByClienteId(this.cliente.getId())).willReturn(anuncios);
	}

	//Ver anuncios
	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion GET de los anuncios de un patrocinador")
	void testVerMisAnuncios() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patrocinador/anuncios")).andExpect(MockMvcResultMatchers.model().attributeExists("paraFiestas")).andExpect(MockMvcResultMatchers.model().attributeExists("paraLocales"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("anuncios/listaAnuncios"));
	}

	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test Negativo para peticion GET de los locales de un propietario que no existe")
	void testNegativoVerMisLocales() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patrocinador/anuncios")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncios")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion GET de los detalles del anuncio ")
	void testDetallesAnuncio() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncios/10")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("anuncios/anuncioDetails"));
	}

	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test Negativo para peticion GET de los detalles de un anuncio cuyo id no existe")
	void testNegativoDetallesAnuncio() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncios/{anuncioId}", this.a2.getId())).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//Crear anuncio para local
	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion GET del formulario de registro de anuncio para local")
	void testFormularioAnuncioForLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/10/local")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
			.andExpect(MockMvcResultMatchers.view().name("anuncios/new"));
	}

	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test negativo para peticion GET del formulario de registro de anuncio para local")
	void testNegativoFormularioAnuncioForLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/10/local")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncio"))
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion POST del formulario de registro de anuncio para local")
	void testNewAnuncioForLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/10/local").with(SecurityMockMvcRequestPostProcessors.csrf()).param("imagen", "https://bangbranding.com/blog/wp-content/uploads/2016/11/700x511_SliderInterior.jpg"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/patrocinador/anuncios"));
	}

	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test negativo para peticion POST del formulario de registro de anuncio para local")
	void testNegativoNewAnuncioForLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/10/local").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("anuncios/new"));
	}

	//Crear anuncio para fiesta
	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion GET del formulario de registro de anuncio para fiesta")
	void testFormularioAnuncioForFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/10/fiesta")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
			.andExpect(MockMvcResultMatchers.view().name("anuncios/new"));
	}

	@WithMockUser(value = "paco")
	@Test
	@DisplayName("Test negativo para peticion GET del formulario de registro de anuncio para fiesta")
	void testNegativoFormularioAnuncioForFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/10/fiesta")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncio"))
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test para peticion POST del formulario de registro de anuncio para fiesta")
	void testNewAnuncioForFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/10/fiesta").with(SecurityMockMvcRequestPostProcessors.csrf()).param("imagen", "https://bangbranding.com/blog/wp-content/uploads/2016/11/700x511_SliderInterior.jpg"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/patrocinador/anuncios"));
	}

	@WithMockUser(value = "george")
	@Test
	@DisplayName("Test negativo para peticion POST del formulario de registro de anuncio para fiesta")
	void testNegativoNewAnuncioForFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/10/fiesta").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("anuncios/new"));
	}

	//Aceptar y rechazar anuncio para local
	@WithMockUser(username = "propietario")
	@Test
	@DisplayName("Test aceptar anuncio para local")
	void testAceptarAnuncioParaLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/anuncio/10/aceptar")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/propietario/anuncios"));
	}

	@WithMockUser(username = "propietario2")
	@Test
	@DisplayName("Test negativo aceptar anuncio para local")
	void testNegativoAceptarAnuncioParaLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/anuncio/10/aceptar")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "propietario")
	@Test
	@DisplayName("Test rechazar anuncio para local")
	void testRechazarAnuncioParaLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/anuncio/10/rechazar")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/propietario/anuncios"));
	}

	@WithMockUser(username = "propietario2")
	@Test
	@DisplayName("Test negativo rechazar anuncio para local")
	void testNegativoRechazarAnuncioParaLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/propietario/anuncio/10/rechazar")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	//Aceptar y rechazar anuncio para fiesta
	@WithMockUser(username = "cliente")
	@Test
	@DisplayName("Test aceptar anuncio para fiesta")
	void testAceptarAnuncioParaFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/anuncio/10/aceptar")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/cliente/anuncios"));
	}

	@WithMockUser(username = "cliente2")
	@Test
	@DisplayName("Test negativo aceptar anuncio para local")
	void testNegativoAceptarAnuncioParaFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/anuncio/10/aceptar")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "cliente")
	@Test
	@DisplayName("Test rechazar anuncio para fiesta")
	void testRechazarAnuncioParaFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/anuncio/10/rechazar")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/cliente/anuncios"));
	}

	@WithMockUser(username = "cliente2")
	@Test
	@DisplayName("Test negativo rechazar anuncio para local")
	void testNegativoRechazarAnuncioParaFiesta() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/cliente/anuncio/10/rechazar")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("exception"));
	}
}
