package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Anuncio;

public interface AnuncioRepository {
	
	Anuncio findById(int id) throws DataAccessException;
	
	Collection<Anuncio> findByPatrocinadorId(int id) throws DataAccessException;

}
