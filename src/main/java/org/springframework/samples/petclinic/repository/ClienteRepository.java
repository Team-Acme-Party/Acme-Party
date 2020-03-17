
package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;

public interface ClienteRepository {

	Cliente findByUsername(String username) throws DataAccessException;

}
