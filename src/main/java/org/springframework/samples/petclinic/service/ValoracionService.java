
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.repository.ValoracionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValoracionService {

	private ValoracionRepository valoracionRepository;


	@Autowired
	public ValoracionService(final ValoracionRepository valoracionRepository) {
		this.valoracionRepository = valoracionRepository;
	}

	@Transactional
	public Collection<Valoracion> findAll() throws DataAccessException {
		return this.valoracionRepository.findAll();
	}

	@Transactional
	public Collection<Valoracion> findByPatrocinadorId(final int id) throws DataAccessException {
		return this.valoracionRepository.findByLocalId(id);
	}

	@Transactional
	public Collection<Valoracion> findByFiestaId(final int id) throws DataAccessException {
		return this.valoracionRepository.findByFiestaId(id);
	}

	@Transactional
	public Collection<Valoracion> findByLocalId(final int id) throws DataAccessException {
		return this.valoracionRepository.findByClienteId(id);
	}

	public Valoracion findById(final int id) throws DataAccessException {
		return this.valoracionRepository.findById(id);
	}

}
