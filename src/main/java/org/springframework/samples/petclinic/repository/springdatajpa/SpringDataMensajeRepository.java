
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Mensaje;
import org.springframework.samples.petclinic.repository.MensajeRepository;

public interface SpringDataMensajeRepository extends MensajeRepository, Repository<Mensaje, Integer> {

	@Override
	@Query("SELECT mensaje FROM Mensaje mensaje")
	Collection<Mensaje> findAll();

	@Override
	@Query("SELECT mensaje FROM Mensaje mensaje WHERE mensaje.id = :id")
	Mensaje findById(@Param("id") int id);

	@Override
	@Query("SELECT mensaje FROM Mensaje mensaje WHERE mensaje.buzonRemitente.id = :id")
	Collection<Mensaje> findByBuzonRemitenteId(@Param("id") int id);

	@Override
	@Query("SELECT mensaje FROM Mensaje mensaje WHERE mensaje.buzonDestinatario.id = :id")
	Collection<Mensaje> findByBuzonDestinatarioId(@Param("id") int id);

}
