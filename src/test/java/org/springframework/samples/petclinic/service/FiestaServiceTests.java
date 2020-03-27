
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FiestaServiceTests {

	@Autowired
	private FiestaService	fiestaService;
	@Autowired
	private ClienteService	clienteService;
	@Autowired
	private LocalService	localService;


	@Test
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
		this.fiestaService.save(newFiesta);
		Collection<Fiesta> despues = this.fiestaService.findAll();
		Assertions.assertEquals(antes.size(), despues.size() - 1);
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
		Boolean contenida = todas.contains(fiesta1);
		Assertions.assertEquals(contenida, true);
	}

}
