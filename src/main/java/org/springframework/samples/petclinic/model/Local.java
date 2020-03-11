package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "fiesta")
public class Local extends BaseEntity {
	
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "capacidad")
	@Range(min = 0)
	private Integer capacidad;
	@Column(name = "condiciones")
	private String condiciones;
	@Column(name = "imagen")
	@URL
	private String imagen;
	@Column(name = "decision")
	@Pattern(regexp = "^(PENDIENTE|ACEPTADO|RECHAZADO)$")
	private String decision;
	@ManyToOne(optional = true)
	@Cascade(CascadeType.DELETE)
	private Propietario propitario;
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Integer getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	public String getCondiciones() {
		return condiciones;
	}
	public void setCondiciones(String condiciones) {
		this.condiciones = condiciones;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public Propietario getPropitario() {
		return propitario;
	}
	public void setPropitario(Propietario propitario) {
		this.propitario = propitario;
	}
	
	

}
