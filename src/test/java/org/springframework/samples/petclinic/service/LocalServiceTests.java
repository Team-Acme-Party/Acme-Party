
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
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
		try {
			this.localService.saveLocal(nuevo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Collection<Local> actualizados = this.localService.findAll();
			Assertions.assertEquals(yaConfirmados.size(), actualizados.size() - 1);
		}
	}

	@Test
	void testNegativoNewLocalPendiente() {
		Propietario propietario = this.propietarioService.findById(1);
		Local nuevo = new Local();
		nuevo.setDireccion("Calle Hermes nº12, 3ºA");
		nuevo.setCapacidad(540);
		nuevo.setCondiciones("Devolver el local tal y como se dejó presentado para su alquiler");
		nuevo.setImagen("https://pmcvariety.files.wordpress.com/2019/04/hush-hush-scene-1.jpg?w=1000&h=563&crop=1");
		nuevo.setPropietario(propietario);
		try {
			this.localService.saveLocal(nuevo);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	@Test
	void testAceptarSolicitud() {
		Local local = this.localService.findLocalById(2);
		try {
			this.localService.aceptarSolicitudLocal(2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Assertions.assertEquals(local.getDecision(), "ACEPTADO");
		}

	}

	@Test
	void testNegativoAceptarSolicitud() {
		try {
			this.localService.aceptarSolicitudLocal(50);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void testRechazarSolicitud() {
		Local local = this.localService.findLocalById(2);
		try {
			this.localService.denegarSolicitudLocal(2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Assertions.assertEquals(local.getDecision(), "RECHAZADO");
		}

	}

	@Test
	void testNegativoRechazarSolicitud() {
		try {
			this.localService.denegarSolicitudLocal(50);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
