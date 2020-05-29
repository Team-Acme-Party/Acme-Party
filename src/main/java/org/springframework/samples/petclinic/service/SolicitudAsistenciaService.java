
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
import org.springframework.samples.petclinic.repository.SolicitudAsistenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class SolicitudAsistenciaService {

	private SolicitudAsistenciaRepository	solicitudAsistenciaRepository;

	@Autowired
	private ClienteService					clienteService;

	@Autowired
	private FiestaService					fiestaService;


	@Autowired
	public SolicitudAsistenciaService(final SolicitudAsistenciaRepository solicitudAsistenciaRepository) {
		this.solicitudAsistenciaRepository = solicitudAsistenciaRepository;
	}

	public SolicitudAsistencia create(final Integer fiestaId, final Cliente cliente) {
		Fiesta fiesta = this.fiestaService.findFiestaById(fiestaId);
		Assert.notNull(fiesta, "No se ha encontrado la fiesta con ese id.");

		Collection<Fiesta> fiestas = this.fiestaService.findAsistenciasByClienteId(cliente.getId());
		Assert.isTrue(!fiestas.contains(fiesta), "Ya has solicitado la asistencia a esta fiesta.");

		String decision = "PENDIENTE";

		SolicitudAsistencia solicitud = new SolicitudAsistencia();
		solicitud.setCliente(cliente);
		solicitud.setFiesta(fiesta);
		solicitud.setDecision(decision);
		return solicitud;
	}

	public Collection<SolicitudAsistencia> findAll() throws DataAccessException {
		return this.solicitudAsistenciaRepository.findAll();
	}

	public SolicitudAsistencia findById(final int id) throws DataAccessException {
		return this.solicitudAsistenciaRepository.findById(id);
	}

	@Transactional
	public void save(final SolicitudAsistencia solicitudAsistencia) throws DataAccessException {
		this.solicitudAsistenciaRepository.save(solicitudAsistencia);
	}

	@Transactional
	public Collection<SolicitudAsistencia> findAsistenciasByClienteId(final int id) throws DataAccessException {
		return this.solicitudAsistenciaRepository.findByClienteId(id);
	}

	public Collection<SolicitudAsistencia> findByFiesta(final Fiesta fiesta) throws DataAccessException {
		return this.solicitudAsistenciaRepository.findByFiesta(fiesta.getId());
	}

	public void aceptarSolicitud(final int id, final Cliente cliente) {
		SolicitudAsistencia solicitud = this.findById(id);
		Assert.isTrue(solicitud.getFiesta().getCliente().equals(cliente), "La fiesta no es del cliente que va tomar la decision");
		solicitud.setDecision("ACEPTADO");
		this.save(solicitud);
	}

	public void rechazarSolicitud(final int id, final Cliente cliente) {
		SolicitudAsistencia solicitud = this.findById(id);
		Assert.isTrue(solicitud.getFiesta().getCliente().equals(cliente), "La fiesta no es del cliente que va tomar la decision");
		solicitud.setDecision("RECHAZADO");
		this.save(solicitud);
	}
	@Transactional
	public Collection<Fiesta> findSolicitudFiestaByClienteId(final int id) throws DataAccessException {
		return this.solicitudAsistenciaRepository.findSolicitudFiestasByClienteId(id);
	}
	

}
