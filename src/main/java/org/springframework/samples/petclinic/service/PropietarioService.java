
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Propietario;
import org.springframework.samples.petclinic.repository.PropietarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class PropietarioService {

	private PropietarioRepository	propietarioRepository;
	@Autowired
	private UserService				userService;
	@Autowired
	private AuthoritiesService		authoritiesService;


	@Autowired
	public PropietarioService(final PropietarioRepository propietarioRepository) {
		this.propietarioRepository = propietarioRepository;
	}

	@Transactional
	public Propietario findByUsername(final String username) throws DataAccessException {
		return this.propietarioRepository.findByUsername(username);
	}

	@Transactional
	public Propietario findById(final int id) throws DataAccessException {
		return this.propietarioRepository.findById(id);
	}

	@Transactional
	public void save(final Propietario propietario) throws DataAccessException {
		this.propietarioRepository.save(propietario);
		this.userService.saveUser(propietario.getUser());
		this.authoritiesService.saveAuthorities(propietario.getUser().getUsername(), "propietario");
	}

	@Transactional
	public Collection<Propietario> findAll() throws DataAccessException {
		return this.propietarioRepository.findAll();
	}
	
	
	public Propietario getPropietarioLogado() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Assert.notNull(username, "Username no logueado");
		Propietario propietario = findByUsername(username);
		return propietario;
	}
	
}
