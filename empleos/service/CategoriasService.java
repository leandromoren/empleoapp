package com.portalempleos.empleos.service;

import java.util.List;

import com.portalempleos.empleos.model.Categoria;

public interface CategoriasService {

	void guardar(Categoria categoria);
	
	List<Categoria> buscarTodas();
	
	Categoria buscarPorId(Integer idCategoria);
	
	void eliminar(int idCategoria);
}

