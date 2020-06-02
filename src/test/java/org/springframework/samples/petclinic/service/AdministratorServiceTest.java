
package org.springframework.samples.petclinic.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AdministratorServiceTest {

	@Autowired
	private AdministradorService administratorService;


	@Test
	@DisplayName("Test positivo dashboard")
	void testPositivoDashboard() {
		Double localP = this.administratorService.localPendiente();
		Double localA = this.administratorService.localAceptado();
		Double localR = this.administratorService.localRechazado();

		Double fiestaA = this.administratorService.fiestaAceptado();
		Double fiestaP = this.administratorService.fiestaPendiente();
		Double fiestaR = this.administratorService.fiestaRechazado();

		Double solicitudA = this.administratorService.solicitudAceptado();
		Double solicitudP = this.administratorService.solicitudPendiente();
		Double solicitudR = this.administratorService.solicitudRechazado();

		Assert.assertNotNull(localP);
		Assert.assertNotNull(localA);
		Assert.assertNotNull(localR);

		Assert.assertNotNull(fiestaA);
		Assert.assertNotNull(fiestaP);
		Assert.assertNotNull(fiestaR);

		Assert.assertNotNull(solicitudA);
		Assert.assertNotNull(solicitudP);
		Assert.assertNotNull(solicitudR);

	}

	@Test
	@DisplayName("Test negativo dashboard")
	void testNegativoDashboard() {
		Double localP = this.administratorService.localPendiente();
		Double localA = this.administratorService.localAceptado();
		Double localR = this.administratorService.localRechazado();

		Double fiestaA = this.administratorService.fiestaAceptado();
		Double fiestaP = this.administratorService.fiestaPendiente();
		Double fiestaR = this.administratorService.fiestaRechazado();

		Double solicitudA = this.administratorService.solicitudAceptado();
		Double solicitudP = this.administratorService.solicitudPendiente();
		Double solicitudR = this.administratorService.solicitudRechazado();

		Assertions.assertNotEquals(0.0, localA);
		Assert.assertNotNull(localP);
		Assert.assertNotNull(localR);

		Assertions.assertNotEquals(0.0, fiestaA);
		Assert.assertNotNull(fiestaP);
		Assert.assertNotNull(fiestaR);

		Assertions.assertNotEquals(0.0, solicitudA);
		Assert.assertNotNull(solicitudP);
		Assert.assertNotNull(solicitudR);

	}

}
