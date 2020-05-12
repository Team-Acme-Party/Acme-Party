
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.URL;

@MappedSuperclass
public class UsuarioEntity extends BaseEntity {

	//Propiedades

	@Column(name = "nombre")
	private String				nombre;

	@Column(name = "apellidos")
	private String				apellidos;

	@Column(name = "email")
	@Email
	private String				email;

	@Column(name = "telefono")
	private String				telefono;

	@Column(name = "foto")
	@URL
	private String				foto;

	//@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User				user;

	@OneToMany
	@Column(name = "mensajes_enviados")
	private Collection<Mensaje>	mensajesEnviados;

	@ManyToMany
	@Column(name = "mensajes_recibidos")
	private Collection<Mensaje>	mensajesRecibidos;


	//Getters y setters

	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return this.apellidos;
	}
	public void setApellidos(final String apellidos) {
		this.apellidos = apellidos;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}
	public String getTelefono() {
		return this.telefono;
	}
	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}
	public String getFoto() {
		return this.foto;
	}
	public void setFoto(final String foto) {
		this.foto = foto;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public Collection<Mensaje> getMensajesEnviados() {
		return this.mensajesEnviados;
	}

	public void setMensajesEnviados(final Collection<Mensaje> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}

	public Collection<Mensaje> getMensajesRecibidos() {
		return this.mensajesRecibidos;
	}

	public void setMensajesRecibidos(final Collection<Mensaje> mensajesRecibidos) {
		this.mensajesRecibidos = mensajesRecibidos;
	}

	@Override
	public String toString() {
		return this.nombre + " " + this.apellidos;
	}

}
