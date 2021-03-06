package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.repository.FiestaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FiestaService {

	private FiestaRepository fiestaRepository;


	@Autowired
	public FiestaService(final FiestaRepository fiestaRepository) {
		this.fiestaRepository = fiestaRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Fiesta> findAll() throws DataAccessException {
		return this.fiestaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<Fiesta> findAccepted() throws DataAccessException {
		return this.fiestaRepository.findAccepted();
	}
	
	@Transactional(readOnly = true)
	public Collection<Fiesta> findRechazado() throws DataAccessException {
		return this.fiestaRepository.findRechazado();
	}
	
	@Transactional(readOnly = true)
	public Collection<Fiesta> findPendiente() throws DataAccessException {
		return this.fiestaRepository.findPendiente();
	}

	@Transactional
	public Fiesta findFiestaById(final int fiestaId) throws DataAccessException {
		return this.fiestaRepository.findById(fiestaId);
	}

	@Transactional
	public Collection<Fiesta> findByClienteId(final int id) throws DataAccessException {
		return this.fiestaRepository.findByClienteId(id);
	}

	@Transactional
	public Collection<Fiesta> findByNombre(String nombre) throws DataAccessException {
		nombre=nombre.toUpperCase();
		return this.fiestaRepository.findByNombre(nombre);
	}

	@Transactional
	public Collection<Fiesta> findAsistenciasByClienteId(final int id) throws DataAccessException {
		return this.fiestaRepository.findAsistenciasByClienteId(id);
	}

	@Transactional
	public void save(final Fiesta f) {
		assert f != null;
		this.fiestaRepository.save(f);
	}

	@Transactional
	public Collection<Fiesta> findFiestasByLocalId(final int localId) throws DataAccessException {
		return this.fiestaRepository.findFiestasByLocalId(localId);
	}

	@Transactional
	public Collection<Fiesta> findFiestasPendientesByLocalId(final int localId) throws DataAccessException {
		return this.fiestaRepository.findFiestasPendientesByLocalId(localId);
	}

	@Transactional
	public Fiesta aceptarSolicitud(final int fiestaId) throws DataAccessException {
		Fiesta fiestaEdit = this.findFiestaById(fiestaId);
		fiestaEdit.setDecision("ACEPTADO");
		this.fiestaRepository.save(fiestaEdit);
		return fiestaEdit;
	}

	@Transactional
	public Fiesta denegarSolicitud(final int fiestaId) throws DataAccessException {
		Fiesta fiestaEdit = this.findFiestaById(fiestaId);
		fiestaEdit.setDecision("RECHAZADO");
		this.fiestaRepository.save(fiestaEdit);
		return fiestaEdit;
	}

}
