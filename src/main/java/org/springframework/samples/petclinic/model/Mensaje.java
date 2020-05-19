
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "mensajes")
public class Mensaje extends BaseEntity {

	//Propiedades

	@Column(name = "asunto")
	@NotBlank
	private String		asunto;

	@Column(name = "cuerpo")
	@NotBlank
	private String		cuerpo;

	@Column(name = "fecha")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	fecha;

	@Column(name = "hora")
	private LocalTime	hora;


	@Column(name = "remitente_id")
	private String		remitente;


	@Column(name = "destinatario_id")
	private String		destinatario;


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

	public LocalTime getHora() {
		return this.hora;
	}

	public void setHora(final LocalTime hora) {
		this.hora = hora;
	}

	public String getRemitente() {
		return this.remitente;
	}

	public void setRemitente(final String remitente) {
		this.remitente = remitente;
	}

	public String getDestinatario() {
		return this.destinatario;
	}

	public void setDestinatario(final String destinatario) {
		this.destinatario = destinatario;
	}

}
