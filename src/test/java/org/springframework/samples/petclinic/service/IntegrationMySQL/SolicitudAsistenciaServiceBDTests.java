package org.springframework.samples.petclinic.service.IntegrationMySQL;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.FiestaService;
import org.springframework.samples.petclinic.service.SolicitudAsistenciaService;
import org.springframework.test.context.TestPropertySource;

//@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@SpringBootTest
@TestPropertySource(locations = "classpath:application-mysql.properties")
public class SolicitudAsistenciaServiceBDTests {
	
	@Autowired
	private SolicitudAsistenciaService solicitudAsistenciaService;
	
	@Autowired
	private ClienteService	clienteService;
	
	@Autowired
	private FiestaService fiestaService;
	
	@Test
	@DisplayName("Test positivo para ver las solicitudes de un cliente")
	void testFindAsistenciasByClienteId() {
		Collection<SolicitudAsistencia> solicitudesAsistencia = new LinkedList<>();
		Cliente cliente = this.clienteService.findById(2);
		solicitudesAsistencia = solicitudAsistenciaService.findAsistenciasByClienteId(cliente.getId());
		assertTrue(!solicitudesAsistencia.isEmpty() && solicitudesAsistencia.size() == 2, "El cliente 2 debe tener 2 solicitudes segun la BD");	
	}
	
	@Test
	@DisplayName("Test negativo para ver las solicitudes de un cliente que no existe")
	void testNegativoFindAsistenciasByClienteId() {
		Collection<SolicitudAsistencia> solicitudesAsistencia = new LinkedList<>();
		Integer idCliente = 99;
		solicitudesAsistencia = solicitudAsistenciaService.findAsistenciasByClienteId(idCliente);
		assertTrue(solicitudesAsistencia.isEmpty() , "El cliente con id 99 no existe debe tener 0 solicitudes segun la BD");	
	}
	
	
	
	//---------------------------------------------
	@Test
	@DisplayName("Test negativo para solicitar asistencia a fiesta por un cliente: cliente es el organizador de la fiesta")
	void testNegativoSolicitarAsistenciaFiesta1() {
		
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Cliente cliente = fiesta.getCliente();
		 
		Assertions.assertThrows(IllegalArgumentException.class, () -> solicitudAsistenciaService.create(fiesta.getId(), cliente));
	}
	
	@Test
	@DisplayName("Test negativo para solicitar asistencia a fiesta por un cliente: cliente ya ha solicitado asistencia a la fiesta")
	void testNegativoSolicitarAsistenciaFiesta2() {
		
		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Cliente cliente = clienteService.findById(2);
		 
		Assertions.assertThrows(IllegalArgumentException.class, () -> solicitudAsistenciaService.create(fiesta.getId(), cliente));
		
	}
	
	@Test
	@DisplayName("Test positivo para solicitar asistencia a fiesta por un cliente")
	void testPositivoSolicitarAsistenciaFiesta() {
		
		Fiesta fiesta = this.fiestaService.findFiestaById(2);
		Cliente cliente = clienteService.findById(2);
		 
		SolicitudAsistencia sa = this.solicitudAsistenciaService.create(fiesta.getId(), cliente);
		solicitudAsistenciaService.save(sa);
		Assertions.assertNotNull(sa);
		Collection<SolicitudAsistencia> saList = solicitudAsistenciaService.findAll();
		Assertions.assertTrue(saList.contains(sa));
	}
	
	//-------------------------------------------
	@Test
	@DisplayName("Test positivo para aceptar una solicitud")
	void testPositivoAceptarSolicitud() {

		Cliente cliente = clienteService.findById(2);
		SolicitudAsistencia solicitud = this.solicitudAsistenciaService.findById(3);
		assertTrue(solicitud.getDecision().equals("PENDIENTE"), "La solicitud no esta pendiente");
		this.solicitudAsistenciaService.aceptarSolicitud(solicitud.getId(), cliente);
		assertTrue(solicitud.getDecision().equals("ACEPTADO"), "La solicitud no esta aceptada");

	}
	
	@Test
	@DisplayName("Test negativo para aceptar una solicitud, la fiesta no pertenece al cliente que va tomar la decision")
	void testNegativoAceptarSolicitud() {

		Cliente cliente = clienteService.findById(1);
		SolicitudAsistencia solicitud = this.solicitudAsistenciaService.findById(3);
		assertTrue(solicitud.getDecision().equals("PENDIENTE"), "La solicitud no esta pendiente");
		Assertions.assertThrows(IllegalArgumentException.class, () ->this.solicitudAsistenciaService.aceptarSolicitud(solicitud.getId(), cliente));

	}
	
	//-------------------------------------------
	@Test
	@DisplayName("Test positivo para rechazar una solicitud")
	void testPositivoRechazarSolicitud() {

		Cliente cliente = clienteService.findById(2);
		SolicitudAsistencia solicitud = this.solicitudAsistenciaService.findById(3);
		assertTrue(solicitud.getDecision().equals("PENDIENTE"), "La solicitud no esta pendiente");
		this.solicitudAsistenciaService.rechazarSolicitud(solicitud.getId(), cliente);
		assertTrue(solicitud.getDecision().equals("RECHAZADO"), "La solicitud no esta aceptada");

	}
	
	@Test
	@DisplayName("Test negativo para Rechazar una solicitud, la fiesta no pertenece al cliente que va tomar la decision")
	void testNegativoRechazarSolicitud() {

		Cliente cliente = clienteService.findById(1);
		SolicitudAsistencia solicitud = this.solicitudAsistenciaService.findById(3);
		assertTrue(solicitud.getDecision().equals("PENDIENTE"), "La solicitud no esta pendiente");
		Assertions.assertThrows(IllegalArgumentException.class, () ->this.solicitudAsistenciaService.rechazarSolicitud(solicitud.getId(), cliente));

	}
}