
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Mensaje;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@AutoConfigureTestDatabase(replace=Replace.NONE)
public class MensajeServiceTest {

	@Autowired
	private MensajeService mensajeService;


	@Test
	@DisplayName("Test positivo listar mis mensajes")
	void testListMensajesPositivo() {
		Collection<Mensaje> mensajes = this.mensajeService.findByDestinatario("cliente1");
		Collection<Mensaje> mensajes2 = this.mensajeService.findByRemitente("cliente1");
		Mensaje mensaje = this.mensajeService.findById(3);
		Mensaje mensaje2 = this.mensajeService.findById(5);
		Mensaje mensaje3 = this.mensajeService.findById(2);
		Assertions.assertEquals(mensajes.contains(mensaje), true);
		Assertions.assertEquals(mensajes.contains(mensaje2), true);
		Assertions.assertEquals(mensajes2.contains(mensaje3), true);
	}

	@Test
	@DisplayName("Test negativo listar mis mensajes")
	void testListMensajesNegativo() {
		Collection<Mensaje> mensajes = this.mensajeService.findByDestinatario("cliente1");
		Collection<Mensaje> mensajes2 = this.mensajeService.findByRemitente("cliente1");
		Mensaje mensaje = this.mensajeService.findById(6);
		Mensaje mensaje2 = this.mensajeService.findById(7);
		Mensaje mensaje3 = this.mensajeService.findById(1);
		Assertions.assertEquals(mensajes.contains(mensaje), false);
		Assertions.assertEquals(mensajes.contains(mensaje2), false);
		Assertions.assertEquals(mensajes2.contains(mensaje3), false);
	}
	
	
	@Test
	@DisplayName("Test positivo crear mensajes")
	void testCrearMensajePositivo() {
		Collection<Mensaje> mensajesAntes = this.mensajeService.findAll();
		Mensaje nuevo = new Mensaje();
		nuevo.setAsunto("asunto");
		nuevo.setCuerpo("cuerpo");
		nuevo.setDestinatario("propietario1");
		nuevo.setRemitente("admin");
		nuevo.setFecha(LocalDate.now());
		nuevo.setId(50);
		nuevo.setHora(LocalTime.now());
		try {
			this.mensajeService.save(nuevo);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			Collection<Mensaje> despues = this.mensajeService.findAll();
			Assertions.assertEquals(mensajesAntes.size(), despues.size()-1 );
		}
	}
	@Test
	@DisplayName("Test negativo crear mensajes")
	void testCrearMensajeNegativo() {
		Mensaje nuevo = new Mensaje();
		nuevo.setAsunto(null);
		nuevo.setCuerpo("cuerpo");
		nuevo.setDestinatario("propietario1");
		nuevo.setRemitente("admin");
		nuevo.setFecha(LocalDate.now());
		nuevo.setId(50);
		nuevo.setHora(LocalTime.now());
		try {
			this.mensajeService.save(nuevo);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


}
