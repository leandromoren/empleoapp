package com.portalempleos.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.portalempleos.empleos.model.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer>{

	
}
