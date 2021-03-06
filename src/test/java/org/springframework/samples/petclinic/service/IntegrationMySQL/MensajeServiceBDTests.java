
package org.springframework.samples.petclinic.service.IntegrationMySQL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Mensaje;
import org.springframework.samples.petclinic.service.MensajeService;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-mysql.properties")
@TestPropertySource(locations = "classpath:application-mysql-travis.properties")
@Transactional
public class MensajeServiceBDTests {

	@Autowired
	private MensajeService mensajeService;

	@Test
	@DisplayName("Test positivo listar mis mensajes")
	void testListMensajesPositivo() throws Exception {
		Collection<Mensaje> mensajes = this.mensajeService.findByDestinatario("cliente1");
		Collection<Mensaje> mensajes2 = this.mensajeService.findByRemitente("cliente1");
		Mensaje mensaje = this.mensajeService.findById(3);
		Mensaje mensaje2 = this.mensajeService.findById(5);
		Mensaje mensaje3 = this.mensajeService.findById(2);
		Boolean existe = false;
		Boolean existe2 = false;
		Boolean existe3 = false;
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
		Assertions.assertEquals(existe, true);
		Assertions.assertEquals(existe2, true);
		Assertions.assertEquals(existe3, true);
	}

	@Test
	@DisplayName("Test negativo listar mis mensajes")
	void testListMensajesNegativo() throws Exception {
		Collection<Mensaje> mensajes = this.mensajeService.findByDestinatario("cliente1");
		Collection<Mensaje> mensajes2 = this.mensajeService.findByRemitente("cliente1");
		Mensaje mensaje = this.mensajeService.findById(6);
		Mensaje mensaje2 = this.mensajeService.findById(7);
		Mensaje mensaje3 = this.mensajeService.findById(1);
		Boolean existe = false;
		Boolean existe2 = false;
		Boolean existe3 = false;
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
		Assertions.assertEquals(existe, false);
		Assertions.assertEquals(existe2, false);
		Assertions.assertEquals(existe3, false);
	}

	@Test
	@DisplayName("Test positivo crear mensajes")
	void testCrearMensajePositivo() throws Exception {
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Collection<Mensaje> despues = this.mensajeService.findAll();
			Assertions.assertEquals(mensajesAntes.size(), despues.size() - 1);
		}
	}

	@Test
	@DisplayName("Test negativo crear mensajes")
	void testCrearMensajeNegativo() throws Exception {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
