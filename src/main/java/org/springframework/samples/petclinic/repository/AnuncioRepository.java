
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Anuncio;

public interface AnuncioRepository {

	Collection<Anuncio> findAll() throws DataAccessException;

	Anuncio findById(int id) throws DataAccessException;

	Collection<Anuncio> findByPatrocinadorId(int id) throws DataAccessException;

	Collection<Anuncio> findByClienteId(int id) throws DataAccessException;

	Collection<Anuncio> findByPropietarioId(int id) throws DataAccessException;

	Collection<Anuncio> findByFiestaId(int id) throws DataAccessException;

	Collection<Anuncio> findByLocalId(int id) throws DataAccessException;

	void save(Anuncio anuncio);

}
