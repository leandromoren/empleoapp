package com.portalempleos.empleos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.portalempleos.empleos.model.Solicitud;

public interface SolicitudesService {

	// EJERCICIO: Metodo que guarda un objeto tipo Solicitud en la BD (solo disponible para un usuario con perfil USUARIO).
		void guardar(Solicitud solicitud);
		
		// EJERCICIO: Metodo que elimina una Solicitud de la BD (solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR).
		void eliminar(Integer idSolicitud);
		
		// EJERCICIO: Metodo que recupera todas las Solicitudes guardadas en la BD (solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR).
		List<Solicitud> buscarTodas();
		
		// EJERCICIO: Metodo que busca una Solicitud en la BD (solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR).
		Solicitud buscarPorId(Integer idSolicitud);
		
		// EJERCICIO: Metodo que recupera todas las Solicitudes (con paginaciÃ³n) guardadas en la BD (solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR).
		Page<Solicitud> buscarTodas(Pageable page);

		
		
		
}
