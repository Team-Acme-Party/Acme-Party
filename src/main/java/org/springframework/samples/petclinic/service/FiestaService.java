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

package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.repository.FiestaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class FiestaService {

	private FiestaRepository fiestaRepository;


	@Autowired
	public FiestaService(final FiestaRepository fiestaRepository) {
		this.fiestaRepository = fiestaRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Fiesta> findAll() throws DataAccessException {
		return this.fiestaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<Fiesta> findAccepted() throws DataAccessException {
		return this.fiestaRepository.findAccepted();
	}

	@Transactional
	public Fiesta findFiestaById(final int fiestaId) throws DataAccessException {
		return this.fiestaRepository.findById(fiestaId);
	}

	@Transactional
	public Collection<Fiesta> findByClienteId(final int id) throws DataAccessException {
		return this.fiestaRepository.findByClienteId(id);
	}

	@Transactional
	public Collection<Fiesta> findByNombre(final String nombre) throws DataAccessException {
		return this.fiestaRepository.findByNombre(nombre);
	}

	@Transactional
	public Collection<Fiesta> findAsistenciasByClienteId(final int id) throws DataAccessException {
		return this.fiestaRepository.findAsistenciasByClienteId(id);
	}

	@Transactional
	public void save(final Fiesta f) {
		assert f != null;
		this.fiestaRepository.save(f);
	}

}
