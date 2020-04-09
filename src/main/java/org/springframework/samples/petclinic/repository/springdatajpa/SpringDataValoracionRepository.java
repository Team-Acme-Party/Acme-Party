
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.repository.ValoracionRepository;

public interface SpringDataValoracionRepository extends ValoracionRepository, Repository<Valoracion, Integer> {

	@Override
	@Query("SELECT valoracion FROM Valoracion valoracion")
	Collection<Valoracion> findAll();

	@Override
	@Query("SELECT valoracion FROM Valoracion valoracion WHERE valoracion.local.id = :id")
	Collection<Valoracion> findByLocalId(@Param("id") int id);

	@Override
	@Query("SELECT valoracion FROM Valoracion valoracion WHERE valoracion.fiesta.id = :id")
	Collection<Valoracion> findByFiestaId(@Param("id") int id);

	@Override
	@Query("SELECT valoracion FROM Valoracion valoracion WHERE valoracion.cliente.id = :id")
	Collection<Valoracion> findByClienteId(@Param("id") int id);

	@Override
	@Query("SELECT valoracion FROM Valoracion valoracion WHERE valoracion.id = :id")
	Valoracion findById(@Param("id") int id);
}
