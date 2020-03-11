package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="propietario")
public class Propietario extends UsuarioEntity {
	

	@OneToMany
	private List<Local> locales;
	
}
