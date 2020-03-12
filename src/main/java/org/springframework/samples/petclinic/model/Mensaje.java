
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "mensajes")
public class Mensaje extends BaseEntity {

	//Propiedades

	@Column(name = "asunto")
	private String		asunto;

	@Column(name = "cuerpo")
	private String		cuerpo;

	@Column(name = "fecha")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private LocalDate	fecha;

	@ManyToOne
	@JoinColumn(name = "remitente_id")
	private Cliente		remitente;

	@ManyToOne
	@JoinColumn(name = "receptor_id")
	private Cliente		receptor;


	//Getters y setters

	public String getAsunto() {
		return this.asunto;
	}

	public void setAsunto(final String asunto) {
		this.asunto = asunto;
	}

	public String getCuerpo() {
		return this.cuerpo;
	}

	public void setCuerpo(final String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	public void setFecha(final LocalDate fecha) {
		this.fecha = fecha;
	}

	public Cliente getRemitente() {
		return this.remitente;
	}

	public void setRemitente(final Cliente remitente) {
		this.remitente = remitente;
	}

	public Cliente getReceptor() {
		return this.receptor;
	}

	public void setReceptor(final Cliente receptor) {
		this.receptor = receptor;
	}

}
