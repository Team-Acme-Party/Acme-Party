
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Mensaje;

public interface MensajeRepository {

	Collection<Mensaje> findAll() throws DataAccessException;

	Mensaje findById(int id) throws DataAccessException;

	Collection<Mensaje> findByBuzonRemitenteId(int id) throws DataAccessException;

	Collection<Mensaje> findByBuzonDestinatarioId(int id) throws DataAccessException;

	void save(Mensaje mensaje) throws DataAccessException;

}
