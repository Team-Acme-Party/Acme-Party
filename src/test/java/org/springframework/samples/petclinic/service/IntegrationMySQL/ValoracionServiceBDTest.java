package org.springframework.samples.petclinic.service.IntegrationMySQL;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.ValoracionService;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-mysql.properties")
@TestPropertySource(locations = "classpath:application-mysql-travis.properties")
@Transactional
public class ValoracionServiceBDTest {
	@Autowired
	private ValoracionService		valoracionService;
	
	@Autowired
	private FiestaService fiestaService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Test
	@DisplayName("Test positivo registrar un comentario")
	void testNewComentario() {	
		Collection<Valoracion> antes = this.valoracionService.findAll();
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Cliente cliente= this.clienteService.findById(1);
		Valoracion valoracion = new Valoracion();
		valoracion.setId(10);
		valoracion.setComentario("Test");		
		valoracion.setFiesta(fiesta);		
		valoracion.setCliente(cliente);
		try {
			this.valoracionService.save(valoracion);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Collection<Valoracion> despues = this.valoracionService.findAll();
			Assertions.assertEquals(antes.size(), despues.size() - 1);
		}
	}
	
	@Test
	@DisplayName("Test negativo registrar un comentario")
	void testNegativoComentario() {	
		Collection<Valoracion> antes = this.valoracionService.findAll();
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Cliente cliente= this.clienteService.findById(1);
		Valoracion valoracion = new Valoracion();
		valoracion.setId(10);
		valoracion.setComentario("Test");		
		valoracion.setFiesta(fiesta);		
		valoracion.setCliente(cliente);
		try {
			this.valoracionService.save(valoracion);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
