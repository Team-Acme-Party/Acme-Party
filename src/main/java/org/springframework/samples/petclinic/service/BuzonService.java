
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Buzon;
import org.springframework.samples.petclinic.repository.BuzonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuzonService {

	private BuzonRepository buzonRepository;


	@Autowired
	public BuzonService(final BuzonRepository buzonRepository) {
		this.buzonRepository = buzonRepository;
	}

	@Transactional
	public Collection<Buzon> findAll() throws DataAccessException {
		return this.buzonRepository.findAll();
	}

	//	@Transactional
	//	public String findUsernameByBuzonId(final int id) throws DataAccessException {
	//		return this.buzonRepository.findUsernameByBuzonId(id);
	//	}

	@Transactional
	public void save(final Buzon b) {
		assert b != null;
		this.buzonRepository.save(b);
	}

}
