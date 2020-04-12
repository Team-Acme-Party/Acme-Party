
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {

	private ComentarioRepository comentarioRepository;


	@Autowired
	public ComentarioService(final ComentarioRepository comentarioRepository) {
		this.comentarioRepository = comentarioRepository;
	}

	@Transactional
	public Collection<Comentario> findAll() throws DataAccessException {
		return this.comentarioRepository.findAll();
	}

	@Transactional
	public Collection<Comentario> findByLocalId(final int id) throws DataAccessException {
		return this.comentarioRepository.findByLocalId(id);
	}

	@Transactional
	public Collection<Comentario> findByFiestaId(final int id) throws DataAccessException {
		return this.comentarioRepository.findByFiestaId(id);
	}

	@Transactional
	public Collection<Comentario> findByClienteId(final int id) throws DataAccessException {
		return this.comentarioRepository.findByClienteId(id);
	}

	public Comentario findById(final int id) throws DataAccessException {
		return this.comentarioRepository.findById(id);
	}

}
