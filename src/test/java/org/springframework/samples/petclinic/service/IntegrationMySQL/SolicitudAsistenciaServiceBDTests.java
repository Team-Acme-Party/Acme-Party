package org.springframework.samples.petclinic.service.IntegrationMySQL;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.LinkedList;

import javax.transaction.Transactional;

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

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-mysql.properties")
@TestPropertySource(locations = "classpath:application-mysql-travis.properties")
@Transactional
public class SolicitudAsistenciaServiceBDTests {

	@Autowired
	private SolicitudAsistenciaService solicitudAsistenciaService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private FiestaService fiestaService;

	@Test
	@DisplayName("Test positivo para ver las solicitudes de un cliente")
	void testFindAsistenciasByClienteId() throws Exception {
		Collection<SolicitudAsistencia> solicitudesAsistencia = new LinkedList<>();
		Cliente cliente = this.clienteService.findById(2);
		solicitudesAsistencia = solicitudAsistenciaService.findAsistenciasByClienteId(cliente.getId());
		SolicitudAsistencia sa1 = solicitudAsistenciaService.findById(2);
		SolicitudAsistencia sa2 = solicitudAsistenciaService.findById(4);

		Boolean contaisSa1 = false;
		Boolean contaisSa2 = false;
		for (SolicitudAsistencia sol : solicitudesAsistencia) {
			if (sol.getId().equals(sa1.getId())) {
				contaisSa1 = true;
			}
			if (sol.getId().equals(sa2.getId())) {
				contaisSa2 = true;
			}
		}

		assertTrue(!solicitudesAsistencia.isEmpty() && contaisSa1 && contaisSa2,
				"El cliente 2 contiene al menos las solicitudes creadas en el data.sql");
	}

	@Test
	@DisplayName("Test negativo para ver las solicitudes de un cliente que no existe")
	void testNegativoFindAsistenciasByClienteId() throws Exception {
		Collection<SolicitudAsistencia> solicitudesAsistencia = new LinkedList<>();
		Integer idCliente = 99;
		solicitudesAsistencia = solicitudAsistenciaService.findAsistenciasByClienteId(idCliente);
		assertTrue(solicitudesAsistencia.isEmpty(),
				"El cliente con id 99 no existe debe tener 0 solicitudes segun la BD");
	}

	@Test
	@DisplayName("Test negativo para solicitar asistencia a fiesta por un cliente: cliente es el organizador de la fiesta")
	void testNegativoSolicitarAsistenciaFiesta1() throws Exception {

		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Cliente cliente = fiesta.getCliente();

		Assertions.assertThrows(IllegalArgumentException.class,
				() -> solicitudAsistenciaService.create(fiesta.getId(), cliente));
	}

	@Test
	@DisplayName("Test negativo para solicitar asistencia a fiesta por un cliente: cliente ya ha solicitado asistencia a la fiesta")
	void testNegativoSolicitarAsistenciaFiesta2() throws Exception {

		Fiesta fiesta = this.fiestaService.findFiestaById(1);
		Cliente cliente = clienteService.findById(2);

		Assertions.assertThrows(IllegalArgumentException.class,
				() -> solicitudAsistenciaService.create(fiesta.getId(), cliente));

	}

	@Test
	@DisplayName("Test positivo para solicitar asistencia a fiesta por un cliente")
	void testPositivoSolicitarAsistenciaFiesta() throws Exception {

		Fiesta fiesta = this.fiestaService.findFiestaById(2);
		Cliente cliente = clienteService.findById(2);

		SolicitudAsistencia sa = this.solicitudAsistenciaService.create(fiesta.getId(), cliente);
		solicitudAsistenciaService.save(sa);
		Assertions.assertNotNull(sa);
		Collection<SolicitudAsistencia> saList = solicitudAsistenciaService.findAll();

		Boolean contais = false;
		for (SolicitudAsistencia sol : saList) {
			if (sol.getId().equals(sa.getId())) {
				contais = true;
				break;
			}
		}
		Assertions.assertTrue(contais);
	}

	@Test
	@DisplayName("Test positivo para aceptar una solicitud")
	void testPositivoAceptarSolicitud() throws Exception {

		Cliente cliente = clienteService.findById(1);
		SolicitudAsistencia solicitud = this.solicitudAsistenciaService.findById(2);
		solicitud.setDecision("PENDIENTE");
		assertTrue(solicitud.getDecision().equals("PENDIENTE"), "La solicitud no esta pendiente");
		this.solicitudAsistenciaService.aceptarSolicitud(solicitud.getId(), cliente);
		SolicitudAsistencia solicitudSaved = this.solicitudAsistenciaService.findById(2);
		assertTrue(solicitudSaved.getDecision().equals("ACEPTADO"), "La solicitud no esta aceptada");

	}

	@Test
	@DisplayName("Test negativo para aceptar una solicitud, la fiesta no pertenece al cliente que va tomar la decision")
	void testNegativoAceptarSolicitud() throws Exception {

		Cliente cliente = clienteService.findById(2);
		SolicitudAsistencia solicitud = this.solicitudAsistenciaService.findById(2);
		assertTrue(solicitud.getDecision().equals("PENDIENTE"), "La solicitud no esta pendiente");
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> this.solicitudAsistenciaService.aceptarSolicitud(solicitud.getId(), cliente));

	}

	@Test
	@DisplayName("Test positivo para rechazar una solicitud")
	void testPositivoRechazarSolicitud() throws Exception {

		Cliente cliente = clienteService.findById(1);
		SolicitudAsistencia solicitud = this.solicitudAsistenciaService.findById(2);
		solicitud.setDecision("PENDIENTE");
		assertTrue(solicitud.getDecision().equals("PENDIENTE"), "La solicitud no esta pendiente");
		this.solicitudAsistenciaService.rechazarSolicitud(solicitud.getId(), cliente);
		SolicitudAsistencia solicitudSaved = this.solicitudAsistenciaService.findById(2);
		assertTrue(solicitudSaved.getDecision().equals("RECHAZADO"), "La solicitud no esta aceptada");

	}

	@Test
	@DisplayName("Test negativo para Rechazar una solicitud, la fiesta no pertenece al cliente que va tomar la decision")
	void testNegativoRechazarSolicitud() throws Exception {

		Cliente cliente = clienteService.findById(2);
		SolicitudAsistencia solicitud = this.solicitudAsistenciaService.findById(2);
		solicitud.setDecision("PENDIENTE");
		assertTrue(solicitud.getDecision().equals("PENDIENTE"), "La solicitud no esta pendiente");
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> this.solicitudAsistenciaService.rechazarSolicitud(solicitud.getId(), cliente));

	}
}