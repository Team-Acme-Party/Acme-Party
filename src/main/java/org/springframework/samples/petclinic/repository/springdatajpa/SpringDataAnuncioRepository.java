
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Anuncio;
import org.springframework.samples.petclinic.repository.AnuncioRepository;

public interface SpringDataAnuncioRepository extends AnuncioRepository, Repository<Anuncio, Integer> {

	@Override
	@Query("SELECT anuncio FROM Anuncio anuncio")
	Collection<Anuncio> findAll();

	@Override
	@Query("SELECT anuncio FROM Anuncio anuncio WHERE anuncio.patrocinador.id = :id")
	Collection<Anuncio> findByPatrocinadorId(@Param("id") int id);

	@Override
	@Query("SELECT anuncio FROM Anuncio anuncio WHERE anuncio.fiesta.cliente.id = :id and anuncio.decision = 'PENDIENTE'")
	Collection<Anuncio> findByClienteId(@Param("id") int id);

	@Override
	@Query("SELECT anuncio FROM Anuncio anuncio WHERE anuncio.local.propietario.id = :id and anuncio.decision = 'PENDIENTE'")
	Collection<Anuncio> findByPropietarioId(@Param("id") int id);

	@Override
	@Query("SELECT anuncio FROM Anuncio anuncio WHERE anuncio.fiesta.id = :id and anuncio.decision = 'ACEPTADO'")
	Collection<Anuncio> findByFiestaId(@Param("id") int id);

	@Override
	@Query("SELECT anuncio FROM Anuncio anuncio WHERE anuncio.local.id = :id and anuncio.decision = 'ACEPTADO'")
	Collection<Anuncio> findByLocalId(@Param("id") int id);

	@Override
	@Query("SELECT anuncio FROM Anuncio anuncio WHERE anuncio.id = :id")
	Anuncio findById(@Param("id") int id);
}
