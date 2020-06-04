package org.springframework.samples.petclinic.service.IntegrationMySQL;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.service.AnuncioService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PatrocinadorService;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-mysql.properties")
@TestPropertySource(locations = "classpath:application-mysql-travis.properties")
@Transactional
public class AnuncioServiceBDTest {

	@Autowired
	private AnuncioService		anuncioService;

	@Autowired
	private PatrocinadorService	patrocinadorService;

	@Autowired
	private LocalService		localService;

	@Autowired
	private FiestaService		fiestaService;


	@Test
	@DisplayName("Test positivo listar mis anuncios")
	void testListAnuncios() throws Exception {
		Collection<Anuncio> anuncios = this.anuncioService.findByPatrocinadorId(1);
		Anuncio anuncio1 = this.anuncioService.findById(1);
		Anuncio anuncio3 = this.anuncioService.findById(3);
		Boolean existe = false;
		Boolean existe2 = false;
		for(Anuncio a : anuncios) {
			if(a.getId() == anuncio1.getId()) existe = true;
		}
		for(Anuncio a : anuncios) {
			if(a.getId() == anuncio3.getId()) existe2 = true;
		}
		
		Assertions.assertEquals(existe, true);
		Assertions.assertEquals(existe2, false);
	}

	@Test
	@DisplayName("Test positivo registro para locales")
	void testNewAnuncioForLocal() throws Exception {
		Collection<Anuncio> before = this.anuncioService.findAll();
		Patrocinador patrocinador = this.patrocinadorService.findById(1);
		Local local = this.localService.findLocalById(1);
		Anuncio anuncio = new Anuncio();
		anuncio.setId(10);
		anuncio.setImagen("http://www.url.com");
		anuncio.setDecision("PENDIENTE");
		anuncio.setPatrocinador(patrocinador);
		anuncio.setLocal(local);
		try {
			this.anuncioService.save(anuncio);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Collection<Anuncio> after = this.anuncioService.findAll();
			Assertions.assertEquals(before.size(), after.size() - 1);
		}
	}

	@Test
	@DisplayName("Test negativo registro para locales")
	void testNegativoNewAnuncioForLocal() throws Exception {
		Patrocinador patrocinador = this.patrocinadorService.findById(1);
		Local local = this.localService.findLocalById(1);
		Anuncio anuncio = new Anuncio();
		anuncio.setId(10);
		anuncio.setImagen("no es url");
		anuncio.setDecision("PENDIENTE");
		anuncio.setPatrocinador(patrocinador);
		anuncio.setLocal(local);
		try {
			this.anuncioService.save(anuncio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Test positivo registro para fiestas")
	void testNewAnuncioForFiesta() throws Exception {
		Collection<Anuncio> before = this.anuncioService.findAll();
		Patrocinador patrocinador = this.patrocinadorService.findById(1);
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Anuncio anuncio = new Anuncio();
		anuncio.setImagen("http://www.url.com");
		anuncio.setDecision("PENDIENTE");
		anuncio.setPatrocinador(patrocinador);
		anuncio.setFiesta(fiesta);
		try {
			this.anuncioService.save(anuncio);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Collection<Anuncio> after = this.anuncioService.findAll();
			Assertions.assertEquals(before.size(), after.size() - 1);
		}
	}

	@Test
	@DisplayName("Test negativo registro para fiestas")
	void testNegativoNewAnuncioForFiesta() throws Exception {
		Patrocinador patrocinador = this.patrocinadorService.findById(1);
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Anuncio anuncio = new Anuncio();
		anuncio.setId(10);
		anuncio.setImagen("no es url");
		anuncio.setDecision("PENDIENTE");
		anuncio.setPatrocinador(patrocinador);
		anuncio.setFiesta(fiesta);
		try {
			this.anuncioService.save(anuncio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Test aceptar anuncio")
	void testAceptarAnuncio() throws Exception {
		Anuncio anuncio = this.anuncioService.findById(4);
		try {
			anuncio = this.anuncioService.aceptar(anuncio);
		} catch (AssertionError ae) {
			ae.printStackTrace();
		} finally {
			Assertions.assertEquals(anuncio.getDecision().equals("ACEPTADO"), true);
		}
	}

	@Test
	@DisplayName("Test negativo aceptar anuncio")
	void testNegativoAceptarAnuncio() throws Exception {
		Anuncio anuncio = this.anuncioService.findById(50);
		try {
			this.anuncioService.aceptar(anuncio);
		} catch (AssertionError ae) {
			ae.printStackTrace();
		}
	}

	@Test
	@DisplayName("Test rechazar anuncio")
	void testRechazarAnuncio() throws Exception {
		Anuncio anuncio = this.anuncioService.findById(4);
		try {
			anuncio = this.anuncioService.rechazar(anuncio);
		} catch (AssertionError ae) {
			ae.printStackTrace();
		} finally {
			Assertions.assertEquals(anuncio.getDecision().equals("RECHAZADO"), true);
		}
	}

	@Test
	@DisplayName("Test negativo rechazar anuncio")
	void testNegativoRechazarAnuncio() throws Exception {
		Anuncio anuncio = this.anuncioService.findById(50);
		try {
			this.anuncioService.rechazar(anuncio);
		} catch (AssertionError ae) {
			ae.printStackTrace();
		}
	}

}