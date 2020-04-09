package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService{
	
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	public ComentarioService(ComentarioRepository comentarioRepository) {
		this.comentarioRepository= comentarioRepository;
	}
	@Transactional
	public Collection<Comentario> findByFiestaId(final int id) throws DataAccessException {
		return this.comentarioRepository.findByFiestaId(id);
	}
	
	@Transactional
	public Collection<Comentario> findByLocalId(final int id) throws DataAccessException {
		return this.comentarioRepository.findByLocalId(id);
	}
	
	public Comentario findById(int id) throws DataAccessException{
		return this.comentarioRepository.findById(id);
	}
	
	@Transactional
	public void save(final Comentario comentario) {
		assert comentario != null;
		this.comentarioRepository.save(comentario);
	}

}
