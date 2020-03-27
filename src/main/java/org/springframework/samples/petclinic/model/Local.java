
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "locales")
public class Local extends BaseEntity {

	//Propiedades

	@NotBlank
	@Column(name = "direccion")
	private String		direccion;

	@NotNull
	@NumberFormat
	@Column(name = "capacidad")
	@Range(min = 0)
	private Integer		capacidad;

	@NotBlank
	@Column(name = "condiciones")
	private String		condiciones;

	@NotBlank
	@Column(name = "imagen")
	@URL
	private String		imagen;

	@Column(name = "decision")
	@Pattern(regexp = "^(PENDIENTE|ACEPTADO|RECHAZADO)$")
	private String		decision;

	@ManyToOne
	@JoinColumn(name = "propietario_id")
	private Propietario	propietario;


	//Getters y setters

	public String getDireccion() {
		return this.direccion;
	}
	public void setDireccion(final String direccion) {
		this.direccion = direccion;
	}
	public Integer getCapacidad() {
		return this.capacidad;
	}
	public void setCapacidad(final Integer capacidad) {
		this.capacidad = capacidad;
	}
	public String getCondiciones() {
		return this.condiciones;
	}
	public void setCondiciones(final String condiciones) {
		this.condiciones = condiciones;
	}
	public String getImagen() {
		return this.imagen;
	}
	public void setImagen(final String imagen) {
		this.imagen = imagen;
	}
	public String getDecision() {
		return this.decision;
	}
	public void setDecision(final String decision) {
		this.decision = decision;
	}
	public Propietario getPropietario() {
		return this.propietario;
	}
	public void setPropietario(final Propietario propietario) {
		this.propietario = propietario;
	}
	@Override
	public String toString() {
		return this.direccion;
	}

	
}
