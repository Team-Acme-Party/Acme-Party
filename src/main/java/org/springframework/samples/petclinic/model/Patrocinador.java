package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="patrocinador")
public class Patrocinador extends UsuarioEntity {
	
	@Column(name = "descripcionExperiencia")
	private String descripcionExperiencia;

	public String getDescripcionExperiencia() {
		return descripcionExperiencia;
	}

	public void setDescripcionExperiencia(String descripcionExperiencia) {
		this.descripcionExperiencia = descripcionExperiencia;
	}
	
	
}
