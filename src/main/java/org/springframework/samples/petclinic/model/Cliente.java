package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="cliente")
public class Cliente extends UsuarioEntity {
	
	@Column(name = "descripcionGustos")
	private String descripcionGustos;
	

	@OneToMany
	private List<Valoracion> valoraciones;
	

	@OneToMany
	private List<Comentario> comentario;
	

	@OneToMany
	private List<SolicitudAsistencia> solicitudes;
	

	@OneToMany
	private List<Fiesta> fiesta;

	public String getDescripcionGustos() {
		return descripcionGustos;
	}

	public void setDescripcionGustos(String descripcionGustos) {
		this.descripcionGustos = descripcionGustos;
	}

	public List<Valoracion> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(List<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}

	public List<Comentario> getComentario() {
		return comentario;
	}

	public void setComentario(List<Comentario> comentario) {
		this.comentario = comentario;
	}

	public List<SolicitudAsistencia> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<SolicitudAsistencia> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public List<Fiesta> getFiesta() {
		return fiesta;
	}

	public void setFiesta(List<Fiesta> fiesta) {
		this.fiesta = fiesta;
	}
	
	
}
