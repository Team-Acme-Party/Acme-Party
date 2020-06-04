
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
	void testPositivoDashboard() throws Exception {
		String localP = this.administratorService.localPendiente();
		String localA = this.administratorService.localAceptado();
		String localR = this.administratorService.localRechazado();
		
		String fiestaA = this.administratorService.fiestaAceptado();
		String fiestaP = this.administratorService.fiestaPendiente();
		String fiestaR = this.administratorService.fiestaRechazado();
		
		String solicitudA = this.administratorService.solicitudAceptado();
		String solicitudP = this.administratorService.solicitudPendiente();
		String solicitudR = this.administratorService.solicitudRechazado();
		
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
	void testNegativoDashboard() throws Exception {
		String localP = this.administratorService.localPendiente();
		String localA = this.administratorService.localAceptado();
		String localR = this.administratorService.localRechazado();
		
		String fiestaA = this.administratorService.fiestaAceptado();
		String fiestaP = this.administratorService.fiestaPendiente();
		String fiestaR = this.administratorService.fiestaRechazado();
		
		String solicitudA = this.administratorService.solicitudAceptado();
		String solicitudP = this.administratorService.solicitudPendiente();
		String solicitudR = this.administratorService.solicitudRechazado();
		
		assertNotEquals("0.0", localA);
		assertNotNull(localP);
		assertNotNull(localR);
		
		assertNotEquals("0.0", fiestaA);
		assertNotNull(fiestaP);
		assertNotNull(fiestaR);
		
		assertNotEquals("0.0", solicitudA);
		assertNotNull(solicitudP);
		assertNotNull(solicitudR);	
		
	}

	

}
