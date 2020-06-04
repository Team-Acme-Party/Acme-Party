
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ComentarioServiceTest {

	@Autowired
	private ComentarioService comentarioService;

	@Autowired
	private FiestaService fiestaService;

	@Autowired
	private ClienteService clienteService;

	@Test
	@DisplayName("Test positivo registrar un comentario")
	void testNewComentario() throws Exception {
		Collection<Comentario> antes = this.comentarioService.findAll();
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Cliente cliente = this.clienteService.findById(1);
		Comentario comentario = new Comentario();
		comentario.setId(10);
		comentario.setCuerpo("Test");
		comentario.setFecha(LocalDate.now());
		comentario.setFiesta(fiesta);
		comentario.setCliente(cliente);
		try {
			this.comentarioService.save(comentario);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Collection<Comentario> despues = this.comentarioService.findAll();
			Assertions.assertEquals(antes.size(), despues.size() - 1);
		}
	}

	@Test
	@DisplayName("Test negativo registrar un comentario")
	void testNegativoComentario() throws Exception {
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Cliente cliente = this.clienteService.findById(1);
		Comentario comentario = new Comentario();
		comentario.setId(10);
		comentario.setCuerpo("");
		comentario.setFecha(LocalDate.now());
		comentario.setFiesta(fiesta);
		comentario.setCliente(cliente);
		try {
			this.comentarioService.save(comentario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
