
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Local;
import org.springframework.samples.petclinic.repository.LocalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalService {

	private LocalRepository localRepository;


	@Autowired
	public LocalService(final LocalRepository localRepository) {
		this.localRepository = localRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Local> findAccepted() throws DataAccessException {
		return this.localRepository.findAccepted();
	}

	@Transactional
	public Local findLocalById(final int localId) throws DataAccessException {
		return this.localRepository.findById(localId);
	}

}
