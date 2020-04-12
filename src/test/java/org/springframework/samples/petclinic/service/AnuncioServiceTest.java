
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AnuncioServiceTest {

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
	void testListAnuncios() {
		Collection<Anuncio> anuncios = this.anuncioService.findByPatrocinadorId(1);
		Anuncio anuncio = this.anuncioService.findById(1);
		Assertions.assertEquals(anuncios.contains(anuncio), true);
	}

	@Test
	@DisplayName("Test positivo mostrar formulario de registro para locales")
	void testNewAnuncioForLocal() {
		Collection<Anuncio> before = this.anuncioService.findAll();
		Patrocinador patrocinador = this.patrocinadorService.findById(1);
		Local local = this.localService.findLocalById(1);
		Anuncio anuncio = new Anuncio();
		anuncio.setId(10);
		anuncio.setImagen("http://www.url.com");
		anuncio.setDecision("PENDIENTE");
		anuncio.setPatrocinador(patrocinador);
		anuncio.setLocal(local);
		this.anuncioService.save(anuncio);
		Collection<Anuncio> after = this.anuncioService.findAll();
		Assertions.assertEquals(before.size(), after.size() - 1);
	}

	@Test
	@DisplayName("Test negativo mostrar formulario de registro para locales")
	void testNegativoNewAnuncioForLocal() {
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

		}
	}

	@Test
	@DisplayName("Test positivo mostrar formulario de registro para fiestas")
	void testNewAnuncioForFiesta() {
		Collection<Anuncio> before = this.anuncioService.findAll();
		Patrocinador patrocinador = this.patrocinadorService.findById(1);
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Anuncio anuncio = new Anuncio();
		anuncio.setImagen("http://www.url.com");
		anuncio.setDecision("PENDIENTE");
		anuncio.setPatrocinador(patrocinador);
		anuncio.setFiesta(fiesta);
		this.anuncioService.save(anuncio);
		Collection<Anuncio> after = this.anuncioService.findAll();
		Assertions.assertEquals(before.size(), after.size() - 1);
	}

	@Test
	@DisplayName("Test negativo mostrar formulario de registro para fiestas")
	void testNegativoNewAnuncioForFiesta() {
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

		}
	}

	@Test
	@DisplayName("Test aceptar anuncio")
	void testAceptarAnuncio() {
		Anuncio anuncio = this.anuncioService.findById(4);
		Anuncio aceptado = this.anuncioService.aceptar(anuncio);
		Assertions.assertEquals(aceptado.getDecision().equals("ACEPTADO"), true);
	}

	@Test
	@DisplayName("Test negativo aceptar anuncio")
	void testNegativoAceptarAnuncio() {
		try {
			Anuncio anuncio = this.anuncioService.findById(50);
			this.anuncioService.aceptar(anuncio);
		} catch (AssertionError e) {
			System.out.println("El anuncio no existe");
		}
	}

	@Test
	@DisplayName("Test rechazar anuncio")
	void testRechazarAnuncio() {
		Anuncio anuncio = this.anuncioService.findById(4);
		Anuncio aceptado = this.anuncioService.rechazar(anuncio);
		Assertions.assertEquals(aceptado.getDecision().equals("RECHAZADO"), true);
	}

	@Test
	@DisplayName("Test negativo rechazar anuncio")
	void testNegativoRechazarAnuncio() {
		try {
			Anuncio anuncio = this.anuncioService.findById(50);
			this.anuncioService.rechazar(anuncio);
		} catch (AssertionError e) {
			System.out.println("El anuncio no existe");
		}
	}

}
