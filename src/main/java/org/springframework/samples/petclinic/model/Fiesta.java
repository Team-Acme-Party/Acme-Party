package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;


@Entity
@Table(name="fiesta")
public class Fiesta extends BaseEntity {

	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "lugar")
	private String descripción;
	
	@Column(name = "precio")
	@Range(min = 0)
	private Double precio;
	
	@Column(name = "requisitos")
	private String requisitos;
	

	@Column(name = "fechaInicio")
	@Type(type = "date")
	private Date fechaInicio;
	
	
	@Column(name = "fechaFin")
	@Type(type = "date")
	private Date fechaFin;
	
	
	@Column(name = "numeroAsistentes")
	@Range(min = 0)
	private Integer numeroAsistentes;
	
	
	@Column(name = "imagen")
	@URL
	private String imagen;
	
	
	@Column(name = "decision")
	@Pattern(regexp = "^(PENDIENTE|ACEPTADO|RECHAZADO)$")
	private String decision;
	
	@ManyToOne(optional = true)
	@Cascade(CascadeType.DELETE)
	private Cliente cliente;
	
	@OneToOne(optional = true)
	private Local local;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripción() {
		return descripción;
	}

	public void setDescripción(String descripción) {
		this.descripción = descripción;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getNumeroAsistentes() {
		return numeroAsistentes;
	}

	public void setNumeroAsistentes(Integer numeroAsistentes) {
		this.numeroAsistentes = numeroAsistentes;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}
	
	

	
	
	

}
