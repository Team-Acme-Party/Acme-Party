package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(name="fiesta")
public class Fiesta extends BaseEntity {
	
	@Column(name = "NOMBRE")
	@Length(max = 20, message = "El campo nombre no puede tener mas de 20 caracteres")
	private String nombre;
	
	@Column(name = "LUGAR")
	@Length(max = 100, message = "El campo lugar no puede tener mas de 100 caracteres")
	private String lugar;
	
	@Column(name = "PRECIO")
	@Range(min = 0, max = 100, message = "El campo precio debe estar entre 0 y 100")
	private Double precio;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	

}
