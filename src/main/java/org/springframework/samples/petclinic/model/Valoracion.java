
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "valoraciones")
public class Valoracion extends BaseEntity {

	@Column(name = "comentario")
	private String comentario;

	@Column(name = "valor")
	@Range(min = 0, max = 5)
	private Double valor;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = " local_id")
	private Local local;

	@ManyToOne
	@JoinColumn(name = " fiesta_id")
	private Fiesta fiesta;

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(final String comentario) {
		this.comentario = comentario;
	}

	public Double getValor() {
		return this.valor;
	}

	public void setValor(final Double valor) {
		this.valor = valor;
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
