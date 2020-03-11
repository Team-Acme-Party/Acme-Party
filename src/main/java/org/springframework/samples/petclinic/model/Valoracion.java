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
@Table(name = "valoracion")
public class Valoracion extends BaseEntity {

	@Column(name = "comentario")
	private String comentario;

	@Column(name = "valoracion")
	@Range(min = 0, max = 5)
	private Integer fecha;

	@ManyToOne(optional = true)
	@Cascade(CascadeType.DELETE)
	private Cliente cliente;

	@ManyToOne(optional = true)
	@Cascade(CascadeType.DELETE)
	private Local local;

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getFecha() {
		return fecha;
	}

	public void setFecha(Integer fecha) {
		this.fecha = fecha;
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
