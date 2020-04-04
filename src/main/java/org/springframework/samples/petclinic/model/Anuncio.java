
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "anuncios")
public class Anuncio extends BaseEntity {

	//Propiedades

	@Column(name = "imagen")
	@NotBlank
	@URL
	private String			imagen;

	@ManyToOne
	@JoinColumn(name = "patrocinador_id")
	private Patrocinador	patrocinador;

	@ManyToOne
	@JoinColumn(name = "fiesta_id")
	private Fiesta			fiesta;

	@ManyToOne
	@JoinColumn(name = "local_id")
	private Local			local;

	@Column(name = "decision")
	@Pattern(regexp = "^(PENDIENTE|ACEPTADO|RECHAZADO)$")
	private String			decision;


	//Getters y setters

	public String getImagen() {
		return this.imagen;
	}
	public void setImagen(final String imagen) {
		this.imagen = imagen;
	}
	public Patrocinador getPatrocinador() {
		return this.patrocinador;
	}
	public void setPatrocinador(final Patrocinador patrocinador) {
		this.patrocinador = patrocinador;
	}
	public Fiesta getFiesta() {
		return this.fiesta;
	}
	public void setFiesta(final Fiesta fiesta) {
		this.fiesta = fiesta;
	}
	public Local getLocal() {
		return this.local;
	}
	public void setLocal(final Local local) {
		this.local = local;
	}
	public String getDecision() {
		return this.decision;
	}
	public void setDecision(final String decision) {
		this.decision = decision;
	}

}
