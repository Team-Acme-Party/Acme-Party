package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "valoracion")
public class Anuncio extends BaseEntity {
	@Column(name = "imagen")
	@URL
	private String imagen;
	@Column(name = "beneficio")
	@Range(min = 0, max = 100)
	private Double beneficio;

	@ManyToOne(optional = true)
	private Patrocinador patrocinador;

	@ManyToOne(optional = true)
	private Fiesta fiesta;

	@ManyToOne(optional = true)
	private Local local;
	@Column(name = "decision")
	@Pattern(regexp = "^(PENDIENTE|ACEPTADO|RECHAZADO)$")
	private String decision;
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public Double getBeneficio() {
		return beneficio;
	}
	public void setBeneficio(Double beneficio) {
		this.beneficio = beneficio;
	}
	public Patrocinador getPatrocinador() {
		return patrocinador;
	}
	public void setPatrocinador(Patrocinador patrocinador) {
		this.patrocinador = patrocinador;
	}
	public Fiesta getFiesta() {
		return fiesta;
	}
	public void setFiesta(Fiesta fiesta) {
		this.fiesta = fiesta;
	}
	public Local getLocal() {
		return local;
	}
	public void setLocal(Local local) {
		this.local = local;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}

	
	
}
