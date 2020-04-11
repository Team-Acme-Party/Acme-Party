
package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ComentarioServiceTests {

	@Autowired
	private LocalService localService;
	
	@Autowired
	private FiestaService fiestaService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ComentarioService comentarioService;

	@Test
	void testCrearComentarioLocal() {
		Collection<Comentario> comentarios = comentarioService.findAll();
		Cliente cliente = this.clienteService.findById(1);
		Local local1 = localService.findLocalById(2);

		Comentario comentario1 = new Comentario();
		comentario1.setCuerpo("Comentario test valido");
		comentario1.setCliente(cliente);
		comentario1.setFecha(LocalDate.now());
		comentario1.setLocal(local1);
		comentario1.setId(32);
		comentarioService.save(comentario1);

		Assertions.assertEquals (comentarios.size(),comentarioService.findAll().size() - 1);

	}

	@Test
	void testCrearComentarioLocalNegativo() {
		Cliente cliente = this.clienteService.findById(1);
		Local local1 = localService.findLocalById(1);

		Comentario comentario1 = new Comentario();
		comentario1.setCuerpo("Comentario test valido");
		comentario1.setCliente(cliente);
		comentario1.setFecha(LocalDate.now());
		comentario1.setLocal(local1);
		comentario1.setId(33);
		
		try {
			comentarioService.save(comentario1);
		} catch (AssertionError e) {

		}
	}
	
	
	@Test
	void testCrearComentarioFiesta() {
		Collection<Comentario> comentarios = comentarioService.findAll();
		Cliente cliente = this.clienteService.findById(2);
		Fiesta fiesta1 = fiestaService.findFiestaById(1);

		Comentario comentario1 = new Comentario();
		comentario1.setCuerpo("Comentario test valido");
		comentario1.setCliente(cliente);
		comentario1.setFecha(LocalDate.now());
		comentario1.setFiesta(fiesta1);
		comentario1.setId(34);
		comentarioService.save(comentario1);

		Assertions.assertEquals (comentarios.size(),comentarioService.findAll().size() - 1);

	}

	@Test
	void testCrearComentarioFiestaNegativo() {
		Cliente cliente = this.clienteService.findById(1);
		Fiesta fiesta1 = fiestaService.findFiestaById(1);

		Comentario comentario1 = new Comentario();
		comentario1.setCuerpo("Comentario test valido");
		comentario1.setCliente(cliente);
		comentario1.setFecha(LocalDate.now());
		comentario1.setFiesta(fiesta1);
		comentario1.setId(31);
		
		try {
			comentarioService.save(comentario1);
		} catch (AssertionError e) {

		}
	}

}
