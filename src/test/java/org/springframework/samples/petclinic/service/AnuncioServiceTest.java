package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AnuncioServiceTest {
	
	@Autowired
	private AnuncioService	anuncioService;
	
	@Autowired
	private PatrocinadorService	patrocinadorService;


	@Test
	void testListAnuncios() {
		Patrocinador patrocinador = this.patrocinadorService.findByUsername("Patrocinador1");
		Collection<Anuncio> anuncios = this.anuncioService.findByPatrocinadorId(patrocinador.getId());
		Anuncio anuncio = this.anuncioService.findById(1);
		Assertions.assertEquals(anuncios.contains(anuncio), true);
		
		
	}

}