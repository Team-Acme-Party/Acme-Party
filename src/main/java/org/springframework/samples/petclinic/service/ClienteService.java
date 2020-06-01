
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ClienteService {

	private ClienteRepository	clienteRepository;
	@Autowired
	private UserService			userService;
	@Autowired
	private AuthoritiesService	authoritiesService;


	@Autowired
	public ClienteService(final ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	public Cliente findByUsername(final String username) throws DataAccessException {
		return this.clienteRepository.findByUsername(username);
	}

	@Transactional
	public Cliente findById(final int id) throws DataAccessException {
		return this.clienteRepository.findById(id);
	}

	@Transactional
	public void save(final Cliente cliente) throws DataAccessException {
		this.clienteRepository.save(cliente);
		this.userService.saveUser(cliente.getUser());
		this.authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
	}

	@Transactional
	public Collection<Cliente> findAll() throws DataAccessException {
		return this.clienteRepository.findAll();
	}
	
	public Cliente getClienteLogado() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Assert.notNull(username, "Username no logueado");
		Cliente cliente = findByUsername(username);
		return cliente;
	}
}
