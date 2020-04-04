
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
	@Query("SELECT anuncio FROM Anuncio anuncio WHERE anuncio.fiesta.id = :id")
	Collection<Anuncio> findByFiestaId(@Param("id") int id);

	@Override
	@Query("SELECT anuncio FROM Anuncio anuncio WHERE anuncio.local.id = :id")
	Collection<Anuncio> findByLocalId(@Param("id") int id);

	@Override
	@Query("SELECT anuncio FROM Anuncio anuncio WHERE anuncio.id = :id")
	Anuncio findById(@Param("id") int id);
}
