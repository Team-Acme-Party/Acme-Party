
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.repository.AnuncioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnuncioService {

	private AnuncioRepository anuncioRepository;


	@Autowired
	public AnuncioService(final AnuncioRepository anuncioRepository) {
		this.anuncioRepository = anuncioRepository;
	}

	@Transactional
	public Collection<Anuncio> findAll() throws DataAccessException {
		return this.anuncioRepository.findAll();
	}

	@Transactional
	public Collection<Anuncio> findByPatrocinadorId(final int id) throws DataAccessException {
		return this.anuncioRepository.findByPatrocinadorId(id);
	}

	@Transactional
	public Collection<Anuncio> findByFiestaId(final int id) throws DataAccessException {
		return this.anuncioRepository.findByFiestaId(id);
	}

	@Transactional
	public Collection<Anuncio> findByLocalId(final int id) throws DataAccessException {
		return this.anuncioRepository.findByLocalId(id);
	}

	public Anuncio findById(final int id) throws DataAccessException {
		return this.anuncioRepository.findById(id);
	}

	@Transactional
	public void save(final Anuncio a) {
		assert a != null;
		this.anuncioRepository.save(a);
	}

}
