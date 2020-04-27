
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "solicitudes_asistencia")
public class SolicitudAsistencia extends BaseEntity {

	//Propiedades

	@ManyToOne
	@JoinColumn(name = "fiesta_id")
	private Fiesta	fiesta;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente	cliente;

	@Column(name = "decision")
	@Pattern(regexp = "^(PENDIENTE|ACEPTADO|RECHAZADO)$")
	private String	decision;


	//Getters y setters

	public Fiesta getFiesta() {
		return this.fiesta;
	}

	public void setFiesta(final Fiesta fiesta) {
		this.fiesta = fiesta;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDecision() {
		return this.decision;
	}

	public void setDecision(final String decision) {
		this.decision = decision;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode() * 31;
	}

	@Override
	public boolean equals(Object o) {
		
		if(o instanceof SolicitudAsistencia) {
			
			SolicitudAsistencia sa = (SolicitudAsistencia) o;
			return this.id == sa.getId();
			
		}else {
			return false;
		}
	}
	
	
	
}
