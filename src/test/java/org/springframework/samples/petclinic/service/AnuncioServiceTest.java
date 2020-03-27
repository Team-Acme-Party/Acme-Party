
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AnuncioServiceTest {

	@Autowired
	private AnuncioService		anuncioService;

	@Autowired
	private PatrocinadorService	patrocinadorService;


	@Test
	void testListAnuncios() {
		Collection<Anuncio> anuncios = this.anuncioService.findByPatrocinadorId(1);
		Anuncio anuncio = this.anuncioService.findById(1);
		Assertions.assertEquals(anuncios.contains(anuncio), true);

	}

}
