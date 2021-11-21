package com.portalempleos.empleos.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.portalempleos.empleos.model.Usuarios;
import com.portalempleos.empleos.repository.UsuariosRepository;
import com.portalempleos.empleos.service.UsuariosService;

@Service
@Primary
public class UsuariosServiceJpa implements UsuariosService{
	
	@Autowired
	private UsuariosRepository usuariosRepo;

	@Override
	public void guardar(Usuarios usuario) {
		usuariosRepo.save(usuario);
	}

	@Override
	public void eliminar(int idUsuario) {
		usuariosRepo.deleteById(idUsuario);
		
	}

	@Override
	public List<Usuarios> buscarTodos() {
		return usuariosRepo.findAll();
	}

	@Override
	public Usuarios buscarPorUsername(String username) {
		return usuariosRepo.findByUsername(username);
	}

}
