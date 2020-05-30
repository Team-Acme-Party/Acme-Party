
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;

public interface SolicitudAsistenciaRepository {

	Collection<SolicitudAsistencia> findByClienteId(int id) throws DataAccessException;

	Collection<SolicitudAsistencia> findAll() throws DataAccessException;

	SolicitudAsistencia findById(int id) throws DataAccessException;

	void save(SolicitudAsistencia solicitudAsistencia) throws DataAccessException;

	Collection<SolicitudAsistencia> findByFiesta(int id) throws DataAccessException;

	Collection<Fiesta> findSolicitudFiestasByClienteId(int id);

	Collection<SolicitudAsistencia> findAccepted() throws DataAccessException;

	Collection<SolicitudAsistencia> findPendiente() throws DataAccessException;

	Collection<SolicitudAsistencia> findRechazado() throws DataAccessException;

}
