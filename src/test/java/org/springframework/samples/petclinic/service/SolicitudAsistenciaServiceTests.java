package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SolicitudAsistenciaServiceTests {
	
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
	
}
