package com.portalempleos.empleos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter{

	/**
	 * Conecta directamente a la base de datos
	 */
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("SELECT username, password, estatus FROM usuarios WHERE username = ?")
		.authoritiesByUsernameQuery("select u.username, p.perfil from usuarioperfil up " + 
		"inner join usuarios u on u.id = up.idUsuario " + 
		"inner join perfiles p on p.id = up.idPerfil " +
		"where u.username = ?");
	}
	
	@Override
	protected void configure(HttpSecurity http)throws Exception {
		//Los recursos estaticos que no requieren autenticacion
		http.authorizeRequests().antMatchers(
				"/bootstrap/**",
				"/images/**",
				"/tinymce/**",
				"/logos/**").permitAll()
		//Las vistas publicas que no requieren autenticacion
		.antMatchers(
				"/",
				"/signup",
				"/search",
				"/bcrypt/**",
				"/about",
				"/vacantes/view/**").permitAll()
		//Asignar permisos por roles
		.antMatchers("/solicitudes/create/**", "/solicitudes/save/**").hasAuthority("USUARIO")
		//Asignar permisos a los distintos catalogos
		.antMatchers("/solicitudes/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		.antMatchers("/vacantes/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		.antMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR","ADMINISTRADOR")
		.antMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADOR")
		//Todas las demas URLs que si requiere autenticacion
		.anyRequest().authenticated()
		//El formulario de login NO requiere autenticacion
		.and().formLogin().loginPage("/login").permitAll()
		.and().logout().permitAll();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
