
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.repository.LocalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalService {

	private LocalRepository localRepository;


	@Autowired
	public LocalService(final LocalRepository localRepository) {
		this.localRepository = localRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Local> findAll() throws DataAccessException {
		return this.localRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<Local> findAccepted() throws DataAccessException {
		return this.localRepository.findAccepted();
	}

	@Transactional(readOnly = true)
	@Cacheable("localPending")
	public Collection<Local> findPending() throws DataAccessException {
		return this.localRepository.findPending();
	}

	@Transactional
	public Local findLocalById(final int localId) throws DataAccessException {
		return this.localRepository.findById(localId);
	}

	@Transactional
	public Collection<Local> findByDireccion(String direccion) throws DataAccessException {
		direccion = direccion.toUpperCase();
		return this.localRepository.findByDireccion(direccion);
	}

	@Transactional
	public Collection<Local> findByPropietarioId(final int id) throws DataAccessException {
		return this.localRepository.findByPropietarioId(id);
	}

	@Transactional
	@CacheEvict(cacheNames = "localPending", allEntries = true)
	public Local denegarSolicitudLocal(final int localId) throws DataAccessException {
		Local localEdit = this.findLocalById(localId);
		localEdit.setDecision("RECHAZADO");
		this.localRepository.save(localEdit);
		return localEdit;
	}

	@Transactional
	@CacheEvict(cacheNames = "localPending", allEntries = true)
	public Local aceptarSolicitudLocal(final int localId) throws DataAccessException {
		Local localEdit = this.findLocalById(localId);
		localEdit.setDecision("ACEPTADO");
		this.localRepository.save(localEdit);
		return localEdit;
	}

	@Transactional
	@CacheEvict(cacheNames = "localPending", allEntries = true)
	public void saveLocal(final Local local) throws DataAccessException {
		this.localRepository.save(local);
	}

}
