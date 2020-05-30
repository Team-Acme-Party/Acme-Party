
package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
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
		
		assertNotNull(localP);
		assertNotNull(localA);
		assertNotNull(localR);
		
		assertNotNull(fiestaA);
		assertNotNull(fiestaP);
		assertNotNull(fiestaR);
		
		assertNotNull(solicitudA);
		assertNotNull(solicitudP);
		assertNotNull(solicitudR);	
		
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
		
		assertNotEquals(0.0, localA);
		assertNotNull(localP);
		assertNotNull(localR);
		
		assertNotEquals(0.0, fiestaA);
		assertNotNull(fiestaP);
		assertNotNull(fiestaR);
		
		assertNotEquals(0.0, solicitudA);
		assertNotNull(solicitudP);
		assertNotNull(solicitudR);	
		
	}

	

}
