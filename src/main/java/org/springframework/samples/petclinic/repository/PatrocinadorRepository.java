
package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Patrocinador;

public interface PatrocinadorRepository {

	Patrocinador findByUsername(String username) throws DataAccessException;

	Patrocinador findById(int id) throws DataAccessException;

}
