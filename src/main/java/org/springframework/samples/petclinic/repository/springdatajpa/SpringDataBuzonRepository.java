
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Buzon;
import org.springframework.samples.petclinic.repository.BuzonRepository;

public interface SpringDataBuzonRepository extends BuzonRepository, Repository<Buzon, Integer> {

	@Override
	@Query("SELECT buzon FROM Buzon buzon")
	Collection<Buzon> findAll();

	//	@Override
	//	@Query("SELECT ua.user.username FROM UsuarioEntity ua WHERE ua.buzon.id = :id")
	//	String findUsernameByBuzonId(@Param("id") int id);

}
