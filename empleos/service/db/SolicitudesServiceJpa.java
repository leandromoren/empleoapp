package com.portalempleos.empleos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.portalempleos.empleos.model.Solicitud;
import com.portalempleos.empleos.repository.SolicitudesRepository;
import com.portalempleos.empleos.service.SolicitudesService;

@Service
public class SolicitudesServiceJpa implements SolicitudesService{
	
	@Autowired
	private SolicitudesRepository solicitudRepo;
	
	

	@Override
	public void guardar(Solicitud solicitud) {
		solicitudRepo.save(solicitud);
		
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		solicitudRepo.deleteById(idSolicitud);
	}

	@Override
	public List<Solicitud> buscarTodas() {
		return solicitudRepo.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		Optional<Solicitud> optional = solicitudRepo.findById(idSolicitud);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		return solicitudRepo.findAll(page);
	}

	@Override
	public boolean existPorIdVacante(Integer id) {
		
		return solicitudRepo.existPorIdVacante(id);
	}


}
