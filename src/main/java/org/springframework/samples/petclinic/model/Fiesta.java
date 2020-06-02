package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "fiestas")
public class Fiesta extends BaseEntity {

	@Column(name = "nombre")
	@NotBlank
	private String		nombre;

	@Column(name = "descripcion")
	@NotBlank
	private String		descripcion;

	@Column(name = "precio")
	@Range(min = 0)
	@NotNull
	@NumberFormat
	private Double		precio;

	@Column(name = "requisitos")
	@NotBlank
	private String		requisitos;

	@Column(name = "fecha")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private LocalDate	fecha;

	@Column(name = "hora_inicio")
	@NotNull
	private LocalTime	horaInicio;

	@Column(name = "hora_fin")
	@NotNull
	private LocalTime	horaFin;
	
	@Column(name = "numero_asistentes")	
	
	@NumberFormat
	private Integer		numeroAsistentes;

	@Column(name = "aforo")
	@Range(min = 0)
	@NotNull
	@NumberFormat
	private Integer		aforo;

	@Column(name = "imagen")
	@URL
	@NotBlank
	private String		imagen;

	@Column(name = "decision")
	@Pattern(regexp = "^(PENDIENTE|ACEPTADO|RECHAZADO)$")
	private String		decision;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente		cliente;

	@ManyToOne
	@JoinColumn(name = "local_id")
	private Local		local;


	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripción) {
		this.descripcion = descripción;
	}

	public Double getPrecio() {
		return this.precio;
	}

	public void setPrecio(final Double precio) {
		this.precio = precio;
	}

	public String getRequisitos() {
		return this.requisitos;
	}

	public void setRequisitos(final String requisitos) {
		this.requisitos = requisitos;
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	public void setFecha(final LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(final LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(final LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public Integer getNumeroAsistentes() {
		return this.numeroAsistentes;
	}

	public void setNumeroAsistentes(final Integer numeroAsistentes) {
		this.numeroAsistentes = numeroAsistentes;
	}
	
	public Integer getAforo() {
		return this.aforo;
	}

	public void setAforo(final Integer aforo) {
		this.aforo = aforo;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(final String imagen) {
		this.imagen = imagen;
	}

	public String getDecision() {
		return this.decision;
	}

	public void setDecision(final String decision) {
		this.decision = decision;
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
	@Override
	public String toString() {
		return this.nombre + "[ "+ this.numeroAsistentes + " personas ]";
	}
}
