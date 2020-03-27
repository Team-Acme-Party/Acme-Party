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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.repository.PatrocinadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PatrocinadorService {

	private PatrocinadorRepository patrocinadorRepository;


	@Autowired
	public PatrocinadorService(final PatrocinadorRepository patrocinadorRepository) {
		this.patrocinadorRepository = patrocinadorRepository;
	}

	@Transactional
	public Patrocinador findByUsername(final String username) throws DataAccessException {
		return this.patrocinadorRepository.findByUsername(username);
	}

	@Transactional
	public Patrocinador findById(final int id) throws DataAccessException {
		return this.patrocinadorRepository.findById(id);
	}
}
