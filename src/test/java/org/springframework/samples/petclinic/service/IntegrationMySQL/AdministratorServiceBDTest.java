
package org.springframework.samples.petclinic.service.IntegrationMySQL;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-mysql.properties")
@TestPropertySource(locations = "classpath:application-mysql-travis.properties")
@Transactional
public class AdministratorServiceBDTest {

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
