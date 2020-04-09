
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Valoracion;

public interface ValoracionRepository {

	Collection<Valoracion> findAll() throws DataAccessException;

	Valoracion findById(int id) throws DataAccessException;

	Collection<Valoracion> findByLocalId(int id) throws DataAccessException;

	Collection<Valoracion> findByFiestaId(int id) throws DataAccessException;

	Collection<Valoracion> findByClienteId(int id) throws DataAccessException;

}
