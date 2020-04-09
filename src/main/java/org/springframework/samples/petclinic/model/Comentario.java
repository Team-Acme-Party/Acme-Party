
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "comentarios")
public class Comentario extends BaseEntity {

	//Propiedades
	@NotBlank
	@Column(name = "cuerpo")
	private String		cuerpo;

	@Column(name = "fecha")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	fecha;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente		cliente;

	@ManyToOne
	@JoinColumn(name = "local_id")
	private Local		local;

	@ManyToOne
	@JoinColumn(name = "fiesta_id")
	private Fiesta		fiesta;


	//Getters y setters

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

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}

	public Local getLocal() {
		return this.local;
	}

	public void setLocal(final Local local) {
		this.local = local;
	}

	public Fiesta getFiesta() {
		return this.fiesta;
	}

	public void setFiesta(final Fiesta fiesta) {
		this.fiesta = fiesta;
	}

}
