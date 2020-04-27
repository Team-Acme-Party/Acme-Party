
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

	@Override
	public int hashCode() {
		return this.id.hashCode() * 31;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o instanceof Cliente) {
			Cliente c = (Cliente) o;
			return this.id == c.getId();
		}else {
			return false;
		}
	}
}
