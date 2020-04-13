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

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
import org.springframework.samples.petclinic.repository.SolicitudAsistenciaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class SolicitudAsistenciaService {

	private SolicitudAsistenciaRepository solicitudAsistenciaRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private FiestaService fiestaService;

	@Autowired
	public SolicitudAsistenciaService(final SolicitudAsistenciaRepository solicitudAsistenciaRepository) {
		this.solicitudAsistenciaRepository = solicitudAsistenciaRepository;
	}

	public SolicitudAsistencia create(Integer fiestaId, Cliente cliente) {
		Fiesta fiesta = fiestaService.findFiestaById(fiestaId);
		Assert.notNull(fiesta,"No se ha encontrado la fiesta con ese id.");

		Assert.isTrue(!fiesta.getCliente().equals(cliente), "Eres es el organizador de la fiesta.");
		
		Collection<Fiesta> fiestas = fiestaService.findAsistenciasByClienteId(cliente.getId());
		Assert.isTrue(!fiestas.contains(fiesta), "Ya has solicitado la asistencia a esta fiesta.");
		
		String decision = "PENDIENTE";
		
		SolicitudAsistencia solicitud = new SolicitudAsistencia();
		solicitud.setCliente(cliente);
		solicitud.setFiesta(fiesta);
		solicitud.setDecision(decision);
		return solicitud;
	}
	
	public Collection<SolicitudAsistencia> findAll() throws DataAccessException {
		return solicitudAsistenciaRepository.findAll();
	}

	public SolicitudAsistencia findById(int id) throws DataAccessException {
		return solicitudAsistenciaRepository.findById(id);
	}

	@Transactional
	public void save(SolicitudAsistencia solicitudAsistencia) throws DataAccessException {
		solicitudAsistenciaRepository.save(solicitudAsistencia);
	}

	@Transactional
	public Collection<SolicitudAsistencia> findAsistenciasByClienteId(final int id) throws DataAccessException {
		return this.solicitudAsistenciaRepository.findByClienteId(id);
	}
	
	public Collection<SolicitudAsistencia> findByFiesta(Fiesta fiesta) throws DataAccessException {
		return this.solicitudAsistenciaRepository.findByFiesta(fiesta.getId());		
	}
	
	public void aceptarSolicitud(int id, Cliente cliente) {
		SolicitudAsistencia solicitud = findById(id);
		Assert.isTrue(solicitud.getFiesta().getCliente().equals(cliente),
				"La fiesta no es del cliente que va tomar la decision");
		solicitud.setDecision("ACEPTADO");
		save(solicitud);
	}
	
	public void rechazarSolicitud(int id,Cliente cliente) {
		SolicitudAsistencia solicitud = findById(id);
		Assert.isTrue(solicitud.getFiesta().getCliente().equals(cliente),
				"La fiesta no es del cliente que va tomar la decision");
		solicitud.setDecision("RECHAZADO");
		save(solicitud);
	}
	
}
