package com.portalempleos.empleos.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.portalempleos.empleos.model.Vacante;

public interface VacantesService {

	List<Vacante> buscarTodas();
	
	Vacante buscarPorId(int idVacante);
	
	void guardar(Vacante vacante);
	
	List<Vacante> buscarDestacadas();
	
	void eliminar(int idVacante);
	
    List<Vacante> buscarByExample(Example<Vacante> example); //Metodo que se encargará de realizar el filtro en la bdd
    
    Page<Vacante> buscarTodas(Pageable page);
}
