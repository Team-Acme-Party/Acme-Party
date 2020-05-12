
package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.URL;

@MappedSuperclass
public class UsuarioEntity extends BaseEntity {

	//Propiedades

	@Column(name = "nombre")
	private String	nombre;

	@Column(name = "apellidos")
	private String	apellidos;

	@Column(name = "email")
	@Email
	private String	email;

	@Column(name = "telefono")
	private String	telefono;

	@Column(name = "foto")
	@URL
	private String	foto;

	//@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User	user;

	@OneToOne
	@Column(name = "buzon_id")
	private Buzon	buzon;


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

	public Buzon getBuzon() {
		return this.buzon;
	}
	public void setBuzon(final Buzon buzon) {
		this.buzon = buzon;
	}

	@Override
	public String toString() {
		return this.nombre + " " + this.apellidos;
	}

}
