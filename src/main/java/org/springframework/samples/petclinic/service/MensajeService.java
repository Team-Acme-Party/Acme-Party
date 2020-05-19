
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Buzon;
import org.springframework.samples.petclinic.model.Mensaje;
import org.springframework.samples.petclinic.repository.BuzonRepository;
import org.springframework.samples.petclinic.repository.MensajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MensajeService {

	private MensajeRepository	mensajeRepository;

	@Autowired
	private BuzonService		buzonService;

	@Autowired
	private MensajeService		mensajeService;


	@Autowired
	public MensajeService(final MensajeRepository mensajeRepository) {
		this.mensajeRepository = mensajeRepository;
	}

	@Transactional
	public Collection<Mensaje> findAll() throws DataAccessException {
		return this.mensajeRepository.findAll();
	}

	@Transactional
	public Mensaje findById(final int id) throws DataAccessException {
		return this.mensajeRepository.findById(id);
	}

	@Transactional
	public Collection<Mensaje> findByRemitente(final String username) throws DataAccessException {
		return this.mensajeRepository.findByRemitente(username);
	}

	@Transactional
	public Collection<Mensaje> findByDestinatario(final String username) throws DataAccessException {
		return this.mensajeRepository.findByDestinatario(username);
	}

	public void save(final Mensaje m) {
		assert m != null;
		Collection<Mensaje> mensajes = this.mensajeService.findAll();
		Collection<Buzon> buzones = this.buzonService.findAll();
//		if (!mensajes.contains(m)) {
//			assert buzones.contains(m.getBuzonDestinatario()) && buzones.contains(m.getBuzonRemitente());
//		}
		this.mensajeRepository.save(m);
	}

}
