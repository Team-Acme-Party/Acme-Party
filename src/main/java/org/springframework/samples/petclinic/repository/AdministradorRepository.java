
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Administrador;

public interface AdministradorRepository {

	Administrador findByUsername(String username) throws DataAccessException;

	Collection<Administrador> findAll() throws DataAccessException;

}
