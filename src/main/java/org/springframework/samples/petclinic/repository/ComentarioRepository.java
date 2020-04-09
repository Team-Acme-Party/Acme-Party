
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Comentario;

public interface ComentarioRepository {

	Collection<Comentario> findAll() throws DataAccessException;

	Comentario findById(int id) throws DataAccessException;

	Collection<Comentario> findByLocalId(int id) throws DataAccessException;

	Collection<Comentario> findByFiestaId(int id) throws DataAccessException;

	Collection<Comentario> findByClienteId(int id) throws DataAccessException;

}
