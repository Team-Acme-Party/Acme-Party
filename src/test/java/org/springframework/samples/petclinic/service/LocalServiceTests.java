
package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LocalServiceTests {

	@Autowired
	private LocalService		localService;
	@Autowired
	private PropietarioService	propietarioService;


	@Test
	void testNewLocalPendiente() {
		Collection<Local> yaConfirmados = this.localService.findAll();
		Propietario propietario = this.propietarioService.findById(1);
		Local nuevo = new Local();
		nuevo.setDireccion("Calle Hermes nº12, 3ºA");
		nuevo.setCapacidad(540);
		nuevo.setCondiciones("Devolver el local tal y como se dejó presentado para su alquiler");
		nuevo.setImagen("https://pmcvariety.files.wordpress.com/2019/04/hush-hush-scene-1.jpg?w=1000&h=563&crop=1");
		nuevo.setDecision("PENDIENTE");
		nuevo.setPropietario(propietario);
		this.localService.saveLocal(nuevo);
		Collection<Local> actualizados = this.localService.findAll();
		Assertions.assertEquals(yaConfirmados.size(), actualizados.size() - 1);
	}

	@Test
	void testNewLocalRechazadoFail() {
		Collection<Local> todos1 = this.localService.findAll();
		Collection<Local> rechazados1 = new ArrayList<Local>();
		for (Local l : todos1) {
			if (l.getDecision().equals("RECHAZADO")) {
				rechazados1.add(l);
			}
		}
		Propietario propietario = this.propietarioService.findById(1);
		Local nuevo = new Local();
		nuevo.setDireccion("Calle Hermes nº12, 3ºA");
		nuevo.setCapacidad(540);
		nuevo.setCondiciones("Devolver el local tal y como se dejó presentado para su alquiler");
		nuevo.setImagen("https://pmcvariety.files.wordpress.com/2019/04/hush-hush-scene-1.jpg?w=1000&h=563&crop=1");
		nuevo.setDecision("RECHAZADO");
		nuevo.setPropietario(propietario);
		this.localService.saveLocal(nuevo);
		Collection<Local> todos2 = this.localService.findAll();
		Collection<Local> rechazados2 = new ArrayList<Local>();
		for (Local l : todos2) {
			if (l.getDecision().equals("RECHAZADO")) {
				rechazados2.add(l);
			}
		}
		Assertions.assertEquals(rechazados1.size(), rechazados2.size() - 1);
	}

	@Test
	void testNewLocalAceptadoFail() {
		Collection<Local> todos1 = this.localService.findAll();
		Collection<Local> aceptados1 = new ArrayList<Local>();
		for (Local l : todos1) {
			if (l.getDecision().equals("ACEPTADO")) {
				aceptados1.add(l);
			}
		}
		Propietario propietario = this.propietarioService.findById(1);
		Local nuevo = new Local();
		nuevo.setDireccion("Calle Hermes nº12, 3ºA");
		nuevo.setCapacidad(540);
		nuevo.setCondiciones("Devolver el local tal y como se dejó presentado para su alquiler");
		nuevo.setImagen("https://pmcvariety.files.wordpress.com/2019/04/hush-hush-scene-1.jpg?w=1000&h=563&crop=1");
		nuevo.setDecision("ACEPTADO");
		nuevo.setPropietario(propietario);
		this.localService.saveLocal(nuevo);
		Collection<Local> todos2 = this.localService.findAll();
		Collection<Local> aceptados2 = new ArrayList<Local>();
		for (Local l : todos2) {
			if (l.getDecision().equals("ACEPTADO")) {
				aceptados2.add(l);
			}
		}
		Assertions.assertEquals(aceptados1.size(), aceptados2.size() - 1);
	}

	@Test
	void testFindByDireccion() {
		Local local = this.localService.findLocalById(1);
		Collection<Local> todos = this.localService.findByDireccion("Luis Montoto 12");
		Boolean contenida = todos.contains(local);
		Assertions.assertEquals(contenida, true);
	}

	@Test
	void testFindByPropietario() {
		Propietario propietario = this.propietarioService.findById(1);
		Collection<Local> locales = this.localService.findByPropietarioId(propietario.getId());
		Local local = this.localService.findLocalById(1);
		Boolean contenido = locales.contains(local);
		Assertions.assertEquals(contenido, true);
	}

}
