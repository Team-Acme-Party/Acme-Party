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
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PatrocinadorService;
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
	private AnuncioService anuncioService;
	
	@MockBean
	private PatrocinadorService patrocinadorService;
	
	@MockBean
	private LocalService localService;
	
	@MockBean
	private FiestaService fiestaService;
	
	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;
	
	private Patrocinador patrocinador;
	
	private Anuncio anuncio;
	private Anuncio a2;
	
	private Local local = new Local();
	private Fiesta fiesta = new Fiesta();
	
	@BeforeEach
    void datosPatrocinador() {
		this.patrocinador = new Patrocinador();
        this.patrocinador.setApellidos("Apellidos prueba");
        this.patrocinador.setEmail("email@prueba.es");
        this.patrocinador.setFoto("http://url.com/%22");
        this.patrocinador.setId(10);
        this.patrocinador.setNombre("george");
        this.patrocinador.setTelefono("654321987");
        this.patrocinador.setDescripcionExperiencia("Descipción experiencia");
        BDDMockito.given(this.patrocinadorService.findByUsername("george")).willReturn(this.patrocinador);
        this.anuncio = new Anuncio();
        this.anuncio.setId(10);
        this.a2 = new Anuncio();
        Collection<Anuncio> anuncios = new LinkedList<Anuncio>();
        anuncios.add(anuncio);
        anuncios.add(a2);
        BDDMockito.given(this.anuncioService.findByPatrocinadorId(this.patrocinador.getId())).willReturn(anuncios);
        BDDMockito.given(this.anuncioService.findById(this.anuncio.getId())).willReturn(anuncio);
    }

	
	//Ver anuncios
//    @WithMockUser(value = "george")
//    @Test
//    @DisplayName("Test para peticion GET de los anuncios de un patrocinador")
//    void testVerMisAnuncios() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/patrocinador/anuncios")).andExpect(MockMvcResultMatchers.model().attributeExists("anuncios")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
//            .andExpect(MockMvcResultMatchers.view().name("anuncios/listaAnuncios"));
//    }
//
//    @WithMockUser(value = "paco")
//    @Test
//    @DisplayName("Test Negativo para peticion GET de los locales de un propietario que no existe")
//    void testNegativoVerMisLocales() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/patrocinador/anuncios")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncios")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
//            .andExpect(MockMvcResultMatchers.view().name("exception"));
//    }
//    
//    @WithMockUser(value = "george")
//    @Test
//    @DisplayName("Test para peticion GET de los detalles del anuncio ")
//    void testDetallesAnuncio() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/{anuncioId}",this.anuncio.getId())).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
//            .andExpect(MockMvcResultMatchers.view().name("anuncios/anuncioDetails"));
//    }
//    
//    @WithMockUser(value = "george")
//    @Test
//    @DisplayName("Test Negativo para peticion GET de los detalles de un anuncio cuyo id no existe")
//    void testNegativoDetallesAnuncio() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/{anuncioId}",this.a2.getId())).andExpect(MockMvcResultMatchers.status().is4xxClientError());
//    }
    
    
    //Crear anuncio para local
	@WithMockUser(value = "george")
    @Test
    @DisplayName("Test para peticion GET del formulario de registro de anuncio para local")
    void testFormularioAnuncioForLocal() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/{targetId}/local", this.local.getId()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
		.andExpect(MockMvcResultMatchers.view().name("anuncios/new"));
    }
	
//	@WithMockUser(value = "paco")
//    @Test
//    @DisplayName("Test negativo para peticion GET del formulario de registro de anuncio para local")
//    void testNegativoFormularioAnuncioForLocal() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/{targetId}/local", this.local.getId()))
//		.andExpect(MockMvcResultMatchers.status().isOk())
//		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncio"))
//		.andExpect(MockMvcResultMatchers.view().name("exception"))
//		;
//    }
//	
//    @WithMockUser(value = "george")
//    @Test
//    @DisplayName("Test para peticion POST del formulario de registro de anuncio para local")
//    void testNewAnuncioForLocal() throws Exception {
//    	this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/{targetId}/local", this.local.getId())
//		.with(SecurityMockMvcRequestPostProcessors.csrf())
//		.param("imagen","https://bangbranding.com/blog/wp-content/uploads/2016/11/700x511_SliderInterior.jpg"))
//		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//		.andExpect(MockMvcResultMatchers.view().name("redirect:/anuncio/10"));
//    }
//    
//    @WithMockUser(value = "george")
//    @Test
//    @DisplayName("Test negativo para peticion POST del formulario de registro de anuncio para local")
//    void testNegativoNewAnuncioForLocal() throws Exception {
//    	this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/{targetId}/local", this.local.getId())
//		.with(SecurityMockMvcRequestPostProcessors.csrf()))
//    	.andExpect(MockMvcResultMatchers.status().is4xxClientError());
//    }
    
    
    //Crear anuncio para fiesta
//    @WithMockUser(value = "george")
//    @Test
//    @DisplayName("Test para peticion GET del formulario de registro de anuncio para fiesta")
//    void testFormularioAnuncioForFiesta() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/{targetId}/fiesta", this.fiesta.getId()))
//		.andExpect(MockMvcResultMatchers.status().isOk())
//		.andExpect(MockMvcResultMatchers.model().attributeExists("anuncio"))
//		.andExpect(MockMvcResultMatchers.view().name("anuncios/new"));
//    }
//	
//	@WithMockUser(value = "paco")
//    @Test
//    @DisplayName("Test negativo para peticion GET del formulario de registro de anuncio para fiesta")
//    void testNegativoFormularioAnuncioForFiesta() throws Exception {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/anuncio/new/{targetId}/fiesta", this.fiesta.getId()))
//		.andExpect(MockMvcResultMatchers.status().isOk())
//		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("anuncio"))
//		.andExpect(MockMvcResultMatchers.view().name("exception"))
//		;
//    }
//    
//    @WithMockUser(value = "george")
//    @Test
//    @DisplayName("Test para peticion POST del formulario de registro de anuncio para fiesta")
//    void testNewAnuncioForFiesta() throws Exception {
//    	this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/{targetId}/fiesta", this.fiesta.getId())
//		.with(SecurityMockMvcRequestPostProcessors.csrf())
//		.param("imagen","https://bangbranding.com/blog/wp-content/uploads/2016/11/700x511_SliderInterior.jpg"))
//		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//		.andExpect(MockMvcResultMatchers.view().name("redirect:/anuncio/10"));
//    }
//    
//    @WithMockUser(value = "george")
//    @Test
//    @DisplayName("Test negativo para peticion POST del formulario de registro de anuncio para fiesta")
//    void testNegativoNewAnuncioForFiesta() throws Exception {
//    	this.mockMvc.perform(MockMvcRequestBuilders.post("/anuncio/new/{targetId}/fiesta", this.fiesta.getId())
//		.with(SecurityMockMvcRequestPostProcessors.csrf()))
//    	.andExpect(MockMvcResultMatchers.status().is4xxClientError());
//    }
    
    
    
}