
package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Propietario;

public interface PropietarioRepository {

	Propietario findByUsername(String username) throws DataAccessException;

	Propietario findById(int id) throws DataAccessException;
	void save(Propietario propietario) throws DataAccessException;

}
