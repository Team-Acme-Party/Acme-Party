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
@Table(name = "mensaje")
public class Mensaje extends BaseEntity {

	@Column(name = "asunto")
	private String asunto;

	@Column(name = "cuerpo")
	private String cuerpo;

	@Column(name = "fecha")
	@Type(type = "date")
	private Date fecha;
	
	@Column(name = "remitente")
	private Integer remitente;

	@Column(name = "receptor")
	private Integer receptor;

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

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

	public Integer getRemitente() {
		return remitente;
	}

	public void setRemitente(Integer remitente) {
		this.remitente = remitente;
	}

	public Integer getReceptor() {
		return receptor;
	}

	public void setReceptor(Integer receptor) {
		this.receptor = receptor;
	}



	

}
