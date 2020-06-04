package org.springframework.samples.petclinic.service.IntegrationMySQL;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.service.LocalService;
import org.springframework.samples.petclinic.service.PropietarioService;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-mysql.properties")
@TestPropertySource(locations = "classpath:application-mysql-travis.properties")
@Transactional
public class LocalServiceBDTests {

	@Autowired
	private LocalService localService;
	@Autowired
	private PropietarioService propietarioService;

	@Test
	void testNewLocalPendiente() throws Exception {
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
	void testNegativoNewLocalPendiente() throws Exception {
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
	void testFindByDireccion() throws Exception {
		Local local = this.localService.findLocalById(1);
		Collection<Local> todos = this.localService.findByDireccion("Luis Montoto 12");
		Boolean existe = false;
		for (Local l : todos) {
			if (l.getId().equals(local.getId())) {
				existe = true;
			}
		}
		Assertions.assertEquals(existe, true);
	}

	@Test
	void testFindByPropietario() throws Exception {
		Propietario propietario = this.propietarioService.findById(1);
		Collection<Local> locales = this.localService.findByPropietarioId(propietario.getId());
		Local local = this.localService.findLocalById(1);
		Boolean existe = false;
		for (Local l : locales) {
			if (l.getId().equals(local.getId())) {
				existe = true;
			}
		}
		Assertions.assertEquals(existe, true);
	}

	@Test
	void testAceptarSolicitud() throws Exception {
		Local local = this.localService.findLocalById(5);
		try {
			local = this.localService.aceptarSolicitudLocal(5);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Assertions.assertEquals(local.getDecision(), "ACEPTADO");
		}

	}

	@Test
	void testNegativoAceptarSolicitud() throws Exception {
		try {
			this.localService.aceptarSolicitudLocal(50);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	void testRechazarSolicitud() throws Exception {
		Local local = this.localService.findLocalById(5);
		try {
			local = this.localService.denegarSolicitudLocal(5);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Assertions.assertEquals(local.getDecision(), "RECHAZADO");
		}

	}

	@Test
	void testNegativoRechazarSolicitud() throws Exception {
		try {
			this.localService.denegarSolicitudLocal(50);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}