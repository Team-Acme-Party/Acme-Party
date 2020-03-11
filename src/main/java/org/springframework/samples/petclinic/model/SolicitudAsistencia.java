package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;


@Entity
@Table(name="solicitudAsistencia")
public class SolicitudAsistencia extends BaseEntity {
	

	@ManyToOne(optional = true)
	private Fiesta fiesta;
	

	@ManyToOne(optional = true)
	private Cliente cliente;
	
	@Column(name = "decision")
	@Pattern(regexp = "^(PENDIENTE|ACEPTADO|RECHAZADO)$")
	private String decision;

	public Fiesta getFiesta() {
		return fiesta;
	}

	public void setFiesta(Fiesta fiesta) {
		this.fiesta = fiesta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}
	
	
	
}
