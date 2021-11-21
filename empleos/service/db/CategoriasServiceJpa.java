package com.portalempleos.empleos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.portalempleos.empleos.model.Categoria;
import com.portalempleos.empleos.repository.CategoriasRepository;
import com.portalempleos.empleos.service.CategoriasService;

@Service
@Primary
public class CategoriasServiceJpa implements CategoriasService{

	@Autowired
	private CategoriasRepository categoriasRepo;
	
	
	@Override
	public void guardar(Categoria categoria) {
		categoriasRepo.save(categoria);
		
	}

	@Override
	public List<Categoria> buscarTodas() {
		return categoriasRepo.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> optional = categoriasRepo.findById(idCategoria);
		if(optional.isPresent()) {
			return optional.get();
		} 
		return null;
		
	}

	@Override
	public void eliminar(int idCategoria) {
		categoriasRepo.deleteById(idCategoria);
		
	}

}
