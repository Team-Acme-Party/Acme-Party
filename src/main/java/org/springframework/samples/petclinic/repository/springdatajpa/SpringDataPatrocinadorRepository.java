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
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.repository.PatrocinadorRepository;

/**
 * Spring Data JPA specialization of the {@link OwnerRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataPatrocinadorRepository extends PatrocinadorRepository, Repository<Patrocinador, Integer> {

	@Override
	@Query("SELECT patrocinador FROM Patrocinador patrocinador WHERE patrocinador.user.username =:username")
	Patrocinador findByUsername(@Param("username") String username);

	@Override
	@Query("SELECT p FROM Patrocinador p WHERE p.id =:id")
	Patrocinador findById(@Param("id") int id);

	@Override
	@Query("SELECT patrocinador FROM Patrocinador patrocinador")
	Collection<Patrocinador> findAll();

}
