
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Vet;

public interface FiestaRepository {

	Collection<Fiesta> findAccepted() throws DataAccessException;

	Fiesta findById(int id) throws DataAccessException;

	Collection<Fiesta> findByNombre(String nombre) throws DataAccessException;
	
	Collection<Fiesta> findFiestasByLocalId(int localId) throws DataAccessException;

	void save(Fiesta fiesta) throws DataAccessException;
}
 