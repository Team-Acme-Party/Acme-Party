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
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.repository.LocalRepository;

/**
 * Spring Data JPA specialization of the {@link OwnerRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataLocalRepository extends LocalRepository, Repository<Local, Integer> {

	@Override
	@Query("SELECT DISTINCT local FROM Local local WHERE local.decision = 'ACEPTADO'")
	Collection<Local> findAccepted();

	@Override
	@Query("SELECT DISTINCT local FROM Local local WHERE local.decision = 'PENDIENTE'")
	Collection<Local> findPending();

	@Override
	@Query("SELECT local FROM Local local WHERE local.id =:id")
	Local findById(@Param("id") int id);

	@Override
	@Query("SELECT local FROM Local local WHERE local.decision = 'ACEPTADO' and UPPER(local.direccion) LIKE %:direccion%")
	Collection<Local> findByDireccion(@Param("direccion") String direccion);

	@Override
	@Query("SELECT local FROM Local local WHERE local.propietario.id = :id")
	Collection<Local> findByPropietarioId(@Param("id") int id);

}
