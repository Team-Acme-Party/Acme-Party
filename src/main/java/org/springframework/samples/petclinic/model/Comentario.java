package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "comentario")
public class Comentario extends BaseEntity {

	@Column(name = "cuerpo")
	private String cuerpo;

	@Column(name = "fecha")
	@Type(type = "date")
	private Date fecha;
	
	@ManyToOne(optional = true)
	@Cascade(CascadeType.DELETE)
	private Cliente cliente;

	@ManyToOne(optional = true)
	@Cascade(CascadeType.DELETE)
	private Local local;
	
	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	

}
