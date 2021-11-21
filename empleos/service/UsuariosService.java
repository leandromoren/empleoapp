package com.portalempleos.empleos.service;

import java.util.List;

import com.portalempleos.empleos.model.Usuarios;

public interface UsuariosService {

	void guardar(Usuarios usuario);
	void eliminar(int idUsuario); 
	List<Usuarios> buscarTodos();
	Usuarios buscarPorUsername(String username);
}
