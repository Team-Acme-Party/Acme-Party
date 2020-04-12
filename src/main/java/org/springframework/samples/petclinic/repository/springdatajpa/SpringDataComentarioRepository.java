
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.repository.ComentarioRepository;

public interface SpringDataComentarioRepository extends ComentarioRepository, Repository<Comentario, Integer> {

	@Override
	@Query("SELECT comentario FROM Comentario comentario")
	Collection<Comentario> findAll();

	@Override
	@Query("SELECT comentario FROM Comentario comentario WHERE comentario.local.id = :id")
	Collection<Comentario> findByLocalId(@Param("id") int id);

	@Override
	@Query("SELECT comentario FROM Comentario comentario WHERE comentario.fiesta.id = :id")
	Collection<Comentario> findByFiestaId(@Param("id") int id);

	@Override
	@Query("SELECT comentario FROM Comentario comentario WHERE comentario.cliente.id = :id")
	Collection<Comentario> findByClienteId(@Param("id") int id);

	@Override
	@Query("SELECT comentario FROM Comentario comentario WHERE comentario.id = :id")
	Comentario findById(@Param("id") int id);
}
