
package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll()
		.antMatchers(HttpMethod.GET, "/", "/welcome", "/oups").permitAll()
		.antMatchers("/propietario/new", "/cliente/new", "/patrocinador/new").permitAll()
		.antMatchers("/users/new").permitAll()
		.antMatchers("/admin/**").hasAnyAuthority("admin")
		.antMatchers("/fiestas/**").permitAll()
		.antMatchers("/locales/**").permitAll()
		.antMatchers("/local/**").hasAnyAuthority("propietario")
		.antMatchers("/propietario/**").hasAnyAuthority("propietario")
		.antMatchers("/patrocinador/**").hasAnyAuthority("patrocinador")
		.antMatchers("/anuncio/**").hasAnyAuthority("patrocinador")
		.antMatchers("/anuncios/**").hasAnyAuthority("patrocinador", "cliente", "admin", "propietario")
		.antMatchers("/cliente/**").hasAnyAuthority("cliente")
		.antMatchers("/administrador/**").hasAnyAuthority("admin")
		.antMatchers("/comentario/**").hasAnyAuthority("cliente")
		.antMatchers("/valoracion/**").hasAnyAuthority("cliente")
		.antMatchers("/usuario/**").hasAnyAuthority("cliente", "admin", "propietario", "patrocinador")
		.antMatchers("/mensajes/**").hasAnyAuthority("cliente", "admin", "propietario", "patrocinador")

			.anyRequest().denyAll().and().formLogin()
			/* .loginPage("/login") */
			.failureUrl("/login-error").and().logout().logoutSuccessUrl("/");
		// Configuración para que funcione la consola de administración
		// de la BD H2 (deshabilitar las cabeceras de protección contra
		// ataques de tipo csrf y habilitar los framesets si su contenido
		// se sirve desde esta misma página.
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource)
				.usersByUsernameQuery("select username,password,enabled " + "from users " + "where username = ?")
				.authoritiesByUsernameQuery("select username, authority " + "from authorities " + "where username = ?")
				.passwordEncoder(this.passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
		return encoder;
	}

}
