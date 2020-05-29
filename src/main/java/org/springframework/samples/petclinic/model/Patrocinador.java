
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "patrocinadores")
public class Patrocinador extends UsuarioEntity {

	@Column(name = "descripcion_experiencia")
	private String descripcionExperiencia;

	public String getDescripcionExperiencia() {
		return this.descripcionExperiencia;
	}

	public void setDescripcionExperiencia(final String descripcionExperiencia) {
		this.descripcionExperiencia = descripcionExperiencia;
	}

}
