package com.portalempleos.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portalempleos.empleos.model.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
	
	/**
	 * Metodo que busca por username
	 */
	Usuarios findByUsername(String username);
}
