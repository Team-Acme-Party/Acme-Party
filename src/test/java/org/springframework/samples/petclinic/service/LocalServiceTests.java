
package org.springframework.samples.petclinic.service;

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
