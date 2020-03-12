
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "propietarios")
public class Propietario extends UsuarioEntity {

}
