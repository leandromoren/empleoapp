package com.portalempleos.empleos.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "usuarios")
public class Usuarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private String username;
	private String password;
	private String email;
	private int estatus;
	private Date fechaRegistro;
	@ManyToMany(fetch=FetchType.EAGER) //Esta config sirve para que cuando haga la busqueda de un usuario automaticamente se recupere en la misma consulta tdos los perfiles que tenga asociados dicho usuario
	@JoinTable(name = "usuarioperfil",
	         joinColumns = @JoinColumn(name = "idUsuario"),
			 inverseJoinColumns = @JoinColumn(name = "idPerfil")
			)
	private List<Perfiles> perfilesMix;
	
	
	public void agregar(Perfiles tempPerfiles) {
		if(perfilesMix == null) {
			perfilesMix = new LinkedList<Perfiles>();
		}
		perfilesMix.add(tempPerfiles); //perfil que se le asigna al usuario
	}
	
	
	
	
	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Perfiles> getPerfilesMix() {
		return perfilesMix;
	}
	public void setPerfilesMix(List<Perfiles> perfilesMix) {
		this.perfilesMix = perfilesMix;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", nombre=" + nombre + ", username=" + username
				+ ", password=" + password + ", estatus=" + estatus + ", fechaRegistro=" + fechaRegistro + "]";
	}
	
	
	
	
}
