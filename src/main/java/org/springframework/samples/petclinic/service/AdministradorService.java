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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.model.SolicitudAsistencia;
import org.springframework.samples.petclinic.repository.AdministradorRepository;
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
public class AdministradorService {

	private AdministradorRepository administradorRepository;

	@Autowired
	private LocalService localService;

	@Autowired
	private FiestaService fiestaService;

	@Autowired
	private SolicitudAsistenciaService solicitudAsistenciaService;

	@Autowired
	public AdministradorService(final AdministradorRepository administradorRepository) {
		this.administradorRepository = administradorRepository;
	}

	@Transactional
	public Administrador findByUsername(final String username) throws DataAccessException {
		return this.administradorRepository.findByUsername(username);
	}

	@Transactional
	public Collection<Administrador> findAll() throws DataAccessException {
		return this.administradorRepository.findAll();
	}

	public Administrador getAdministradorLogado() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Assert.notNull(username, "Username no logueado");
		Administrador admin = findByUsername(username);
		return admin;
	}

	public String localAceptado() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		Collection<Local> locales = this.localService.findAll();
		Collection<Local> localesA = this.localService.findAccepted();
		Double resultado = null;
		if (locales.size() != 0) {
			resultado = ((double) (100 * localesA.size()) / (double) locales.size());
		} else {
			resultado = 0.00;
		}
		return (df.format(resultado));
	}

	public String localRechazado() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		Collection<Local> locales = this.localService.findAll();
		Collection<Local> localesA = this.localService.findRechazado();
		Double resultado = null;
		if (locales.size() != 0) {
			resultado = ((double) (100 * localesA.size()) / (double) locales.size());
		} else {
			resultado = 0.00;
		}
		return (df.format(resultado));
	}

	public String localPendiente() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		Collection<Local> locales = this.localService.findAll();
		Collection<Local> localesA = this.localService.findPending();
		Double resultado = null;
		if (locales.size() != 0) {
			resultado = ((double) (100 * localesA.size()) / (double) locales.size());
		} else {
			resultado = 0.00;
		}
				return (df.format(resultado));
	}

	public String fiestaAceptado() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		Collection<Fiesta> fiesta = this.fiestaService.findAll();
		Collection<Fiesta> fiestaA = this.fiestaService.findAccepted();
		Double resultado = null;
		if (fiesta.size() != 0) {
			resultado = ((double) (100 * fiestaA.size()) / (double) fiesta.size());
		} else {
			resultado = 0.00;
		}
				return (df.format(resultado));
	}

	public String fiestaRechazado() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		Collection<Fiesta> fiesta = this.fiestaService.findAll();
		Collection<Fiesta> fiestaA = this.fiestaService.findRechazado();
		Double resultado = null;
		if (fiesta.size() != 0) {
			resultado = ((double) (100 * fiestaA.size()) / (double) fiesta.size());
		} else {
			resultado = 0.00;
		}
				return (df.format(resultado));
	}

	public String fiestaPendiente() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		Collection<Fiesta> fiesta = this.fiestaService.findAll();
		Collection<Fiesta> fiestaA = this.fiestaService.findPendiente();
		Double resultado = null;
		if (fiesta.size() != 0) {
			resultado = ((double) (100 * fiestaA.size()) / (double) fiesta.size());
		} else {
			resultado = 0.00;
		}
				return (df.format(resultado));
	}

	public String solicitudAceptado() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		Collection<SolicitudAsistencia> solicitudAsistencia = this.solicitudAsistenciaService.findAll();
		Collection<SolicitudAsistencia> solicitudAsistenciaA = this.solicitudAsistenciaService.findAccepted();
		Double resultado = null;
		if (solicitudAsistencia.size() != 0) {
			resultado = ((double) (100 * solicitudAsistenciaA.size()) / (double) solicitudAsistencia.size());
		} else {
			resultado = 0.00;
		}
				return (df.format(resultado));
	}

	public String solicitudRechazado() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		Collection<SolicitudAsistencia> solicitudAsistencia = this.solicitudAsistenciaService.findAll();
		Collection<SolicitudAsistencia> solicitudAsistenciaA = this.solicitudAsistenciaService.findRechazado();
		Double resultado = null;
		if (solicitudAsistencia.size() != 0) {
			resultado = ((double) (100 * solicitudAsistenciaA.size()) / (double) solicitudAsistencia.size());
		} else {
			resultado = 0.00;
		}
				return (df.format(resultado));
	}

	public String solicitudPendiente() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		Collection<SolicitudAsistencia> solicitudAsistencia = this.solicitudAsistenciaService.findAll();
		Collection<SolicitudAsistencia> solicitudAsistenciaA = this.solicitudAsistenciaService.findPendiente();
		Double resultado = null;
		if (solicitudAsistencia.size() != 0) {
			resultado = ((double) (100 * solicitudAsistenciaA.size()) / (double) solicitudAsistencia.size());
		} else {
			resultado = 0.00;
		}
				return (df.format(resultado));
	}
}
