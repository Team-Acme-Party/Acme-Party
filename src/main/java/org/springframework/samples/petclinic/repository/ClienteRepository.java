
package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Propietario;

public interface ClienteRepository {

	Cliente findByUsername(String username) throws DataAccessException;

	Cliente findById(int id) throws DataAccessException;
	void save(Cliente cliente) throws DataAccessException;
}
