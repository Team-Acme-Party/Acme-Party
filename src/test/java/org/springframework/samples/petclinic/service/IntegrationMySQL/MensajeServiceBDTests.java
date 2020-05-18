
package org.springframework.samples.petclinic.service.IntegrationMySQL;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Mensaje;
import org.springframework.samples.petclinic.service.MensajeService;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class MensajeServiceBDTests {

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
		Boolean existe = false;
		Boolean existe2 = false;
		Boolean existe3 = false;
		Boolean existe4 = false;
		for (Mensaje m : mensajes) {
			if (m.getId() == mensaje.getId()) {
				existe = true;
			}
		}
		for (Mensaje m : mensajes) {
			if (m.getId() == mensaje2.getId()) {
				existe2 = true;
			}
		}
		for (Mensaje m : mensajes2) {
			if (m.getId() == mensaje3.getId()) {
				existe3 = true;
			}
		}
		for (Mensaje m : mensajes2) {
			if (m.getId() == mensaje4.getId()) {
				existe4 = true;
			}
		}
		Assertions.assertEquals(existe, true);
		Assertions.assertEquals(existe2, true);
		Assertions.assertEquals(existe3, true);
		Assertions.assertEquals(existe4, true);
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
		Boolean existe = false;
		Boolean existe2 = false;
		Boolean existe3 = false;
		Boolean existe4 = false;
		for (Mensaje m : mensajes) {
			if (m.getId() == mensaje.getId()) {
				existe = true;
			}
		}
		for (Mensaje m : mensajes) {
			if (m.getId() == mensaje2.getId()) {
				existe2 = true;
			}
		}
		for (Mensaje m : mensajes2) {
			if (m.getId() == mensaje3.getId()) {
				existe3 = true;
			}
		}
		for (Mensaje m : mensajes2) {
			if (m.getId() == mensaje4.getId()) {
				existe4 = true;
			}
		}
		Assertions.assertEquals(existe, false);
		Assertions.assertEquals(existe2, false);
		Assertions.assertEquals(existe3, false);
		Assertions.assertEquals(existe4, false);
	}

}
