package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.repository.AnuncioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnuncioService{
	
	private AnuncioRepository anuncioRepository;
	
	@Autowired
	public AnuncioService(AnuncioRepository anuncioRepository) {
		this.anuncioRepository= anuncioRepository;
	}
	@Transactional
	public Collection<Anuncio> findByPatrocinadorId(final int id) throws DataAccessException {
		return this.anuncioRepository.findByPatrocinadorId(id);
	}
	
	public Anuncio findById(int id) throws DataAccessException{
		return this.anuncioRepository.findById(id);
	}

}
