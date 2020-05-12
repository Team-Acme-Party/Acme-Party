
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
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

	//	@ManyToOne
	//	@JoinColumn(name = "remitente_username")
	//	private UsuarioEntity	remitenteUsername;
	//
	//	@ManyToOne
	//	@JoinColumn(name = "receptor_username")
	//	private UsuarioEntity	receptorUsername;


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

	//	public UsuarioEntity getRemitente() {
	//		return this.remitenteUsername;
	//	}
	//
	//	public void setRemitente(final UsuarioEntity remitenteUsername) {
	//		this.remitenteUsername = remitenteUsername;
	//	}
	//
	//	public UsuarioEntity getReceptor() {
	//		return this.receptorUsername;
	//	}
	//
	//	public void setReceptor(final UsuarioEntity receptorUsername) {
	//		this.receptorUsername = receptorUsername;
	//	}

}
