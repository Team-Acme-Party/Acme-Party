/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.repository.FiestaRepository;

/**
 * Spring Data JPA specialization of the {@link OwnerRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataFiestaRepository extends FiestaRepository, Repository<Fiesta, Integer> {

	@Override
	@Query("SELECT DISTINCT fiesta FROM Fiesta fiesta WHERE fiesta.decision = 'ACEPTADO'")
	Collection<Fiesta> findAccepted();
	
	@Override
	@Query("SELECT DISTINCT fiesta FROM Fiesta fiesta WHERE fiesta.decision = 'PENDIENTE'")
	Collection<Fiesta> findPendiente();
	
	@Override
	@Query("SELECT DISTINCT fiesta FROM Fiesta fiesta WHERE fiesta.decision = 'RECHAZADO'")
	Collection<Fiesta> findRechazado();

	@Override
	@Query("SELECT fiesta FROM Fiesta fiesta WHERE fiesta.id =:id")
	Fiesta findById(@Param("id") int id);

	@Override
	@Query("SELECT fiesta FROM Fiesta fiesta WHERE fiesta.decision = 'ACEPTADO' and UPPER(fiesta.nombre) LIKE %:nombre%")
	Collection<Fiesta> findByNombre(@Param("nombre") String nombre);

	@Override

	@Query("SELECT fiesta FROM Fiesta fiesta WHERE fiesta.cliente.id =:id")
	Collection<Fiesta> findByClienteId(@Param("id") int id);

	@Override
	@Query("SELECT DISTINCT fiesta FROM Fiesta fiesta WHERE fiesta.local.id =:localId")
	Collection<Fiesta> findFiestasByLocalId(@Param("localId") int localId);

	@Override
	@Query("SELECT DISTINCT fiesta FROM Fiesta fiesta WHERE fiesta.local.id =:localId and fiesta.decision = 'PENDIENTE'")
	Collection<Fiesta> findFiestasPendientesByLocalId(@Param("localId") int localId);
	
	@Override
	@Query("select sa.fiesta from SolicitudAsistencia sa where sa.cliente.id = :clienteId")
	Collection<Fiesta> findAsistenciasByClienteId(@Param("clienteId") int clienteId);
}
