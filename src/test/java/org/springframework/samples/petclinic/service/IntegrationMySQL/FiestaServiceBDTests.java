
package org.springframework.samples.petclinic.service.IntegrationMySQL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.LinkedList;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-mysql-travis.properties")
@Transactional
public class FiestaServiceBDTests {

	@Autowired
	private FiestaService	fiestaService;
	@Autowired
	private ClienteService	clienteService;
	@Autowired
	private LocalService	localService;


	@Test
	@DisplayName("Test positivo registrar una fiesta")
	void testNewFiesta() {
		Collection<Fiesta> antes = this.fiestaService.findAll();
		Cliente cliente = this.clienteService.findById(1);
		Local local = this.localService.findLocalById(1);
		Fiesta newFiesta = new Fiesta();
		newFiesta.setNombre("Test");
		newFiesta.setDescripcion("Test");
		newFiesta.setPrecio(12.);
		newFiesta.setRequisitos("Test");
		newFiesta.setFecha(LocalDate.now());
		newFiesta.setHoraInicio(LocalTime.parse("22:00"));
		newFiesta.setHoraFin(LocalTime.parse("05:00"));
		newFiesta.setNumeroAsistentes(50);
		newFiesta.setImagen("https://elcaso.elnacional.cat/uploads/s1/78/21/46/fiesta-fin-de-ano-confeti-pixabay.jpeg");
		newFiesta.setDecision("PENDIENTE");
		newFiesta.setCliente(cliente);
		newFiesta.setLocal(local);
		try {
			this.fiestaService.save(newFiesta);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Collection<Fiesta> despues = this.fiestaService.findAll();
			Assertions.assertEquals(antes.size(), despues.size() - 1);
		}
	}

	@Test
	@DisplayName("Test negativo registrar una fiesta")
	void testNegativoNewFiesta() {
		Cliente cliente = this.clienteService.findById(1);
		Local local = this.localService.findLocalById(1);
		Fiesta newFiesta = new Fiesta();
		newFiesta.setNombre("Test");
		newFiesta.setDescripcion("Test");
		newFiesta.setPrecio(12.);
		newFiesta.setRequisitos("Test");
		newFiesta.setFecha(LocalDate.now());
		newFiesta.setHoraInicio(LocalTime.parse("22:00"));
		newFiesta.setHoraFin(LocalTime.parse("05:00"));
		newFiesta.setNumeroAsistentes(50);
		newFiesta.setDecision("PENDIENTE");
		newFiesta.setCliente(cliente);
		newFiesta.setLocal(local);
		try {
			this.fiestaService.save(newFiesta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Test editar una fiesta")
	void testEditarFiesta() {
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		fiesta.setDescripcion("Testing");
		try {
			this.fiestaService.save(fiesta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Test negativo editar una fiesta")
	void testNegativoEditarFiesta() {
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		fiesta.setCliente(null);
		try {
			this.fiestaService.save(fiesta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testFindAccepted() {
		Cliente cliente = this.clienteService.findById(1);
		Local local = this.localService.findLocalById(1);
		Fiesta newFiesta = new Fiesta();
		newFiesta.setNombre("Test");
		newFiesta.setDescripcion("Test");
		newFiesta.setPrecio(12.);
		newFiesta.setRequisitos("Test");
		newFiesta.setFecha(LocalDate.now());
		newFiesta.setHoraInicio(LocalTime.parse("22:00"));
		newFiesta.setHoraFin(LocalTime.parse("05:00"));
		newFiesta.setNumeroAsistentes(50);
		newFiesta.setImagen("https://elcaso.elnacional.cat/uploads/s1/78/21/46/fiesta-fin-de-ano-confeti-pixabay.jpeg");
		newFiesta.setDecision("PENDIENTE");
		newFiesta.setCliente(cliente);
		newFiesta.setLocal(local);
		this.fiestaService.save(newFiesta);
		Collection<Fiesta> aceptadas = this.fiestaService.findAccepted();
		Boolean contenida = aceptadas.contains(newFiesta);
		Assertions.assertEquals(contenida, false);
	}

	@Test
	void testFindByNombre() {
		Fiesta fiesta1 = this.fiestaService.findFiestaById(1);
		Collection<Fiesta> todas = this.fiestaService.findByNombre("Fiesta de disfraces");
		Boolean contenida = false;
		for(Fiesta a : todas) {
			if(a.getId() == fiesta1.getId()) contenida = true;
		}
		Assertions.assertEquals(contenida, true);
	}

	@Test
	@DisplayName("Test positivo para ver las fiestas organizadas por un cliente")
	void testFindFiestasByClienteId() {
		Collection<Fiesta> fiestas = new LinkedList<>();
		Cliente cliente = this.clienteService.findById(2);
		fiestas = this.fiestaService.findByClienteId(cliente.getId());
		Assertions.assertTrue(!fiestas.isEmpty() && fiestas.size() == 2, "El cliente 2 debe tener 2 fiestas organizadas segun la BD");
	}

	@Test
	@DisplayName("Test negativo para ver las fiestas organizadas por un cliente que no existe")
	void testNegativoFindFiestasByClienteId() {
		Collection<Fiesta> fiestas = new LinkedList<>();
		Integer idCliente = -1;
		fiestas = this.fiestaService.findByClienteId(idCliente);
		Assertions.assertTrue(fiestas.isEmpty(), "El cliente con id -1 no existe, no debe tener ninguna fiesta");
	}

	@Test
	@DisplayName("Test positivo aceptar fiesta")
	void testAceptarFiesta() {
		try {
			this.fiestaService.aceptarSolicitud(2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Fiesta fiesta = this.fiestaService.findFiestaById(2);
			Assertions.assertEquals(fiesta.getDecision(), "ACEPTADO");
		}
	}

	@Test
	@DisplayName("Test negativo aceptar fiesta")
	void testNegativoAceptarFiesta() {
		try {
			this.fiestaService.aceptarSolicitud(50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Test positivo rechazar fiesta")
	void testRechazarFiesta() {
		try {
			this.fiestaService.denegarSolicitud(2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Fiesta fiesta = this.fiestaService.findFiestaById(2);
			Assertions.assertEquals(fiesta.getDecision(), "RECHAZADO");
		}
	}

	@Test
	@DisplayName("Test negativo aceptar fiesta")
	void testNegativoRechazarFiesta() {
		try {
			this.fiestaService.denegarSolicitud(50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
