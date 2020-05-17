
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Buzon;

public interface BuzonRepository {

	Collection<Buzon> findAll() throws DataAccessException;

	void save(Buzon buzon) throws DataAccessException;

	//	String findUsernameByBuzonId(int id);

}
