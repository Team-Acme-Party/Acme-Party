
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;

public interface SolicitudAsistenciaRepository {

	Collection<SolicitudAsistencia> findByClienteId(int id) throws DataAccessException;

	Collection<Fiesta> findSolicitudFiestasByClienteId(int id);


}
