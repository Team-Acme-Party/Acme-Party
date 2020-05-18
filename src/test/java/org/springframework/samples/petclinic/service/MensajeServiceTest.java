
package org.springframework.samples.petclinic.service;

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
public class MensajeServiceTest {

	@Autowired
	private MensajeService mensajeService;


	@Test
	@DisplayName("Test positivo listar mis mensajes")
	void testListMensajesPositivo() {
		Collection<Mensaje> mensajes = this.mensajeService.findByBuzonDestinatarioId(5);
		Collection<Mensaje> mensajes2 = this.mensajeService.findByBuzonRemitenteId(5);
		Mensaje mensaje = this.mensajeService.findById(4);
		Mensaje mensaje2 = this.mensajeService.findById(5);
		Mensaje mensaje3 = this.mensajeService.findById(6);
		Mensaje mensaje4 = this.mensajeService.findById(3);
		Assertions.assertEquals(mensajes.contains(mensaje), true);
		Assertions.assertEquals(mensajes.contains(mensaje2), true);
		Assertions.assertEquals(mensajes2.contains(mensaje3), true);
		Assertions.assertEquals(mensajes2.contains(mensaje4), true);
	}

	@Test
	@DisplayName("Test negativo listar mis mensajes")
	void testListMensajesNegativo() {
		Collection<Mensaje> mensajes = this.mensajeService.findByBuzonDestinatarioId(5);
		Collection<Mensaje> mensajes2 = this.mensajeService.findByBuzonRemitenteId(5);
		Mensaje mensaje = this.mensajeService.findById(6);
		Mensaje mensaje2 = this.mensajeService.findById(7);
		Mensaje mensaje3 = this.mensajeService.findById(1);
		Mensaje mensaje4 = this.mensajeService.findById(2);
		Assertions.assertEquals(mensajes.contains(mensaje), false);
		Assertions.assertEquals(mensajes.contains(mensaje2), false);
		Assertions.assertEquals(mensajes2.contains(mensaje3), false);
		Assertions.assertEquals(mensajes2.contains(mensaje4), false);
	}

}
