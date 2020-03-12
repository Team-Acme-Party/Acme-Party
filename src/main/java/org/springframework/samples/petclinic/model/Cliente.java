
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente extends UsuarioEntity {

	//Propiedades

	@Column(name = "descripcion_gustos")
	private String descripcionGustos;


	//Getters y setters

	public String getDescripcionGustos() {
		return this.descripcionGustos;
	}

	public void setDescripcionGustos(final String descripcionGustos) {
		this.descripcionGustos = descripcionGustos;
	}

}
