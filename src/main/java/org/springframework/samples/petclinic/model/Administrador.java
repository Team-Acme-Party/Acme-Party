
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administradores")
public class Administrador extends UsuarioEntity {

}
