
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column(name = "buzon_remitente_id")
	private Buzon		buzonRemitente;

	@ManyToOne
	@Column(name = "buzon_destinatario_id")
	private Buzon		buzonDestinatario;


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

	public Buzon getBuzonRemitente() {
		return this.buzonRemitente;
	}

	public void setBuzonRemitente(final Buzon buzonRemitente) {
		this.buzonRemitente = buzonRemitente;
	}

	public Buzon getBuzonDestinatario() {
		return this.buzonDestinatario;
	}

	public void setBuzonDestinatario(final Buzon buzonDestinatario) {
		this.buzonDestinatario = buzonDestinatario;
	}

}
