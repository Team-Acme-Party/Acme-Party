
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Patrocinador;

public interface PatrocinadorRepository {

	Patrocinador findByUsername(String username) throws DataAccessException;

	Patrocinador findById(int id) throws DataAccessException;

	void save(Patrocinador patrocinador) throws DataAccessException;

	Collection<Patrocinador> findAll() throws DataAccessException;

}
