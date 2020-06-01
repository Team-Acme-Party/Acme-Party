
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Patrocinador;
import org.springframework.samples.petclinic.repository.PatrocinadorRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class PatrocinadorService {

	private PatrocinadorRepository	patrocinadorRepository;
	@Autowired
	private UserService				userService;
	@Autowired
	private AuthoritiesService		authoritiesService;


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

	@Transactional
	public void save(final Patrocinador patrocinador) throws DataAccessException {
		this.patrocinadorRepository.save(patrocinador);
		this.userService.saveUser(patrocinador.getUser());
		this.authoritiesService.saveAuthorities(patrocinador.getUser().getUsername(), "patrocinador");
	}

	@Transactional
	public Collection<Patrocinador> findAll() throws DataAccessException {
		return this.patrocinadorRepository.findAll();
	}
	
	public Patrocinador getPatrocinadorLogado() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Assert.notNull(username, "Username no logueado");
		Patrocinador patrocinador = findByUsername(username);
		return patrocinador;
	}
	
}
