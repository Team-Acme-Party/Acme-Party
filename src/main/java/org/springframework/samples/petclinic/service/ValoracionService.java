
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Fiesta;
import org.springframework.samples.petclinic.model.Valoracion;
import org.springframework.samples.petclinic.repository.ValoracionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValoracionService {

	private ValoracionRepository valoracionRepository;
	
	@Autowired
	private SolicitudAsistenciaService	solicitudAsistenciaService;

	@Autowired
	private LocalService				localService;

	@Autowired
	private FiestaService				fiestaService;

	@Autowired
	public ValoracionService(final ValoracionRepository valoracionRepository) {
		this.valoracionRepository = valoracionRepository;
	}

	@Transactional
	public Collection<Valoracion> findAll() throws DataAccessException {
		return this.valoracionRepository.findAll();
	}

	@Transactional
	public Collection<Valoracion> findByPatrocinadorId(final int id) throws DataAccessException {
		return this.valoracionRepository.findByLocalId(id);
	}

	@Transactional
	public Collection<Valoracion> findByFiestaId(final int id) throws DataAccessException {
		return this.valoracionRepository.findByFiestaId(id);
	}

	@Transactional
	public Collection<Valoracion> findByLocalId(final int id) throws DataAccessException {
		return this.valoracionRepository.findByClienteId(id);
	}

	public Valoracion findById(final int id) throws DataAccessException {
		return this.valoracionRepository.findById(id);
	}
	
	@Transactional
	public void save(final Valoracion valoracion) {
		assert valoracion != null;
		Collection<Fiesta> fiestasCliente = this.solicitudAsistenciaService.findSolicitudFiestaByClienteId(valoracion.getCliente().getId());
		if (valoracion.getLocal() != null) {
			assert fiestasCliente.stream().anyMatch(a -> a.getLocal().equals(this.localService.findLocalById(valoracion.getLocal().getId())));
		}
		if (valoracion.getFiesta() != null) {
			assert fiestasCliente.contains(this.fiestaService.findFiestaById(valoracion.getFiesta().getId()));
		}
		this.valoracionRepository.save(valoracion);
	}

}
