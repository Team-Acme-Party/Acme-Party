package org.springframework.samples.petclinic.service.IntegrationMySQL;

import java.time.LocalDate;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-mysql.properties")
@TestPropertySource(locations = "classpath:application-mysql-travis.properties")
@Transactional
public class ComentarioServiceBDTest {

	@Autowired
	private ComentarioService		comentarioService;
	
	@Autowired
	private FiestaService fiestaService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Test
	@DisplayName("Test positivo registrar un comentario")
	void testNewComentario() throws Exception {	
		Collection<Comentario> antes = this.comentarioService.findAll();
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Cliente cliente= this.clienteService.findById(1);
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
		Cliente cliente= this.clienteService.findById(1);
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
