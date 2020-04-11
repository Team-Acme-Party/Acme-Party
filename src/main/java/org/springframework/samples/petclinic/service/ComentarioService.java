package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.repository.ComentarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService{
	
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private SolicitudAsistenciaService		solicitudAsistenciaService;
	
	@Autowired
	private LocalService		localService;
	
	@Autowired
	private FiestaService		fiestaService;
	
	@Autowired
	public ComentarioService(ComentarioRepository comentarioRepository) {
		this.comentarioRepository= comentarioRepository;
	}
	
	@Transactional(readOnly = true)
	public Collection<Comentario> findAll() throws DataAccessException {
		return this.comentarioRepository.findAll();
	}

	
	@Transactional
	public Collection<Comentario> findByFiestaId(final int id) throws DataAccessException {
		return this.comentarioRepository.findByFiestaId(id);
	}
	
	@Transactional
	public Collection<Comentario> findByLocalId(final int id) throws DataAccessException {
		return this.comentarioRepository.findByLocalId(id);
	}
	
	public Comentario findById(int id) throws DataAccessException{
		return this.comentarioRepository.findById(id);
	}
	
	@Transactional
	public void save(final Comentario comentario) {
		assert comentario != null;
		Collection<Fiesta> fiestasCliente = solicitudAsistenciaService.findSolicitudFiestaByClienteId(comentario.getCliente().getId());
		if(comentario.getLocal()!=null) {
			assert(fiestasCliente.stream().anyMatch(a->a.getLocal().equals(localService.findLocalById(comentario.getLocal().getId()))));
		}
		if(comentario.getFiesta()!=null) {
			assert(fiestasCliente.contains(fiestaService.findFiestaById(comentario.getFiesta().getId())));
		}
		this.comentarioRepository.save(comentario);
	}

}
